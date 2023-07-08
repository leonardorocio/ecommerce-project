import { KeyValue } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Observable, filter, share, switchMap } from 'rxjs';
import {
  APIDocs,
  MethodProperties,
  PropertyAttributes,
  SchemaProperties,
} from 'src/app/models/admin';
import { ApiDocsService } from 'src/app/services/api-docs.service';

@Component({
  selector: 'app-admin-input',
  templateUrl: './admin-input.component.html',
  styleUrls: ['./admin-input.component.css'],
})
export class AdminInputComponent implements OnInit {
  constructor(private apiDocs: ApiDocsService, private toastr: ToastrService) {}

  @Input() method!: MethodProperties;
  @Input() docs!: APIDocs;
  @Input() tag!: string;
  requestBody!: SchemaProperties;
  operationReturnData$!: Observable<any>;
  hasRequestBody!: boolean;
  hasParameters!: boolean;
  refsMap: Map<string, any[]> = new Map<string, any[]>();
  paramMap: Map<string, any[]> = new Map<string, any[]>();
  currentRef!: any;
  chosenService!: Function;

  originalOrder = (
    a: KeyValue<string, any>,
    b: KeyValue<string, any>
  ): number => {
    return 0;
  };

  ngOnInit(): void {
    this.hasRequestBody = Object.keys(this.method).includes('requestBody');
    this.hasParameters = Object.keys(this.method).includes('parameters');
    this.chosenService = this.apiDocs.chooseServiceToCall(this.tag);
    this.getParamRefs();
    this.fetchRequestBodyAndRefs();
  }

  getParamRefs() {
    if (this.hasParameters) {
      this.method.parameters.forEach((parameter) => {
        if (parameter.in === 'path' && parameter.name.includes('id')) {
          let operation = this.tag.at(0)?.toUpperCase() + this.tag.substring(1);
          operation = operation.endsWith('s') ? operation : operation + 's';
          this.apiDocs
            .executeOperation(
              this.getFetchOperationNameFromRefOrTag(operation),
              [],
              {},
              this.chosenService
            )
            .subscribe((data) => {
              this.paramMap.set(parameter.name, data);
            });
        }
      });
    }
  }

  getParamRef(key: string): any[] {
    return this.paramMap.get(key) ?? [];
  }

  getRef(key: string): any[] {
    return this.refsMap.get(key) ?? [];
  }

  fetchRequestBodyAndRefs() {
    if (this.hasRequestBody) {
      this.requestBody = this.apiDocs.getRequestBody(this.docs, this.method);
      this.getRefs(this.requestBody);
    }
  }

  getFetchOperationNameFromRefOrTag(ref: string): string {
    let returnRef: string;
    switch (ref) {
      case 'ProductCategorys':
      case 'Categorys':
        returnRef = 'ProductCategories';
        break;
      case 'Address':
        returnRef = 'Addresses';
        break;
      case 'Details':
        returnRef = 'OrderDetails';
        break;
      default:
        returnRef = ref;
        break;
    }
    return 'get' + returnRef;
  }

  getRefs(requestBody: SchemaProperties) {
    Object.entries(requestBody.properties).forEach((field, index) => {
      let [key, value] = field;
      if (value.$ref !== undefined && value.$ref !== null) {
        value.$ref = value.$ref.split('/').pop() ?? '';
        value.$ref = value.$ref.endsWith('s') ? value.$ref : value.$ref + 's';
        const chosenService = this.apiDocs.chooseServiceToCall(
          value.$ref.toLowerCase()
        );
        this.apiDocs
          .executeOperation(
            this.getFetchOperationNameFromRefOrTag(value.$ref),
            [],
            {},
            chosenService
          )
          .subscribe((data) => {
            this.refsMap.set(key, data);
          });
      }
    });
  }

  submitForm(operation: string, requestForm: NgForm) {
    const parameters = Object.entries(requestForm.value)
      .filter((field) => field[0].includes('param'))
      .map((field) => field[1]);
    const requestBody = Object.fromEntries(
      Object.entries(requestForm.value).filter(
        (field) => !field[0].includes('param')
      )
    );
    this.executeOperationFromForm(
      operation,
      parameters,
      requestBody,
      this.chosenService
    );
  }

  executeOperationFromForm(
    operation: string,
    parameters: any[],
    requestBody: {},
    chosenService: Function
  ) {
    this.operationReturnData$ = this.apiDocs
      .executeOperation(operation, parameters, requestBody, chosenService)
      .pipe(
        share(),
        switchMap((data) => {
          return new Observable<any>((observer) => {
            if (data === null || data === undefined) {
              observer.next(data);
              observer.complete();
            }
            const dataTreated: any = Object.fromEntries(
              Object.entries(data).map((prop) => {
                if (prop[1] === null) {
                  prop[1] = '';
                }
                return prop;
              })
            );
            observer.next(dataTreated);
            observer.complete();
          });
        })
      );

    this.operationReturnData$.subscribe((data) => {
      this.getParamRefs();
      if (( Object.keys(data).length > 0)) {
        this.toastr.success(`${operation} concluída com sucesso`, 'OK');
      }
    });
  }
}
