import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Observable, switchMap } from 'rxjs';
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
  currentRef!: any;

  ngOnInit(): void {
    this.hasRequestBody = Object.keys(this.method).includes('requestBody');
    this.hasParameters = Object.keys(this.method).includes('parameters');
    this.fetchRequestBodyAndRefs();
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

  getOperationNameFromRef(ref: string): string {
    let returnRef: string;
    switch (ref) {
      case 'ProductCategorys':
        returnRef = 'ProductCategories';
        break;
      case 'Address':
        returnRef = 'Addresses';
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
        console.log(value.$ref.toLowerCase());
        const chosenService = this.apiDocs.chooseServiceToCall(
          value.$ref.toLowerCase()
        );
        console.log(chosenService);
        console.log(this.getOperationNameFromRef(value.$ref));
        this.apiDocs
          .executeOperation(this.getOperationNameFromRef(value.$ref), [], {}, chosenService)
          .subscribe((data) => {
            this.refsMap.set(key, data);
          });
      }
    });
  }

  submitForm(operation: string, requestForm: NgForm) {
    console.log(requestForm.value);
    const parameters = Object.entries(requestForm.value)
      .filter((field) => field[0].includes('param'))
      .map((field) => field[1]);
    const requestBody = Object.fromEntries(
      Object.entries(requestForm.value).filter(
        (field) => !field[0].includes('param')
      )
    );
    const chosenService = this.apiDocs.chooseServiceToCall(this.tag);
    this.executeOperation(operation, parameters, requestBody, chosenService);
  }

  executeOperation(
    operation: string,
    parameters: any[],
    requestBody: {},
    chosenService: Function
  ) {
    this.operationReturnData$ = this.apiDocs
      .executeOperation(operation, parameters, requestBody, chosenService)
      .pipe(
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
      this.toastr.success(`${operation} concluída com sucesso`, 'OK');
    });
  }
}
