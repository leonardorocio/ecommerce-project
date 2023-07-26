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
  selectedParamModel!: any;
  changeParamsRef: boolean = false;
  chosenService!: Function;
  disableButton: boolean = false;

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
    this.fetchRequestBodyAndRefs();
  }

  setSelectedParamModel(param: any) {
    this.selectedParamModel = param;
  }

  setDisableSubmitButton(option: boolean) {
    this.disableButton = option;
  }

  fetchRequestBodyAndRefs() {
    if (this.hasRequestBody) {
      this.requestBody = this.apiDocs.getRequestBody(this.docs, this.method);
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

  submitForm(operation: string, requestForm: NgForm) {
    console.log(requestForm.value);
    const parameters = Object.entries(requestForm.value)
      .filter((field) => field[0].includes('param'))
      .map((field) =>
        typeof field[1] === 'object' ? (field[1] as any)?.id : field[1]
      );
    const requestBody = Object.fromEntries(
      Object.entries(requestForm.value)
        .filter((field) => !field[0].includes('param'))
        .map((field) => {
          if (typeof field[1] === 'object') {
            field[1] = (field[1] as any)?.id;
          }
          return field;
        })
    );
    this.executeOperationFromForm(
      requestForm,
      operation,
      parameters,
      requestBody,
      this.chosenService
    );
  }

  executeOperationFromForm(
    form: NgForm,
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
              this.toastr.success(`${operation} concluída com sucesso`, 'OK');
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
      if (Object.keys(data ?? {}).length > 0) {
        this.toastr.success(`${operation} concluída com sucesso`, 'OK');
      }
      this.changeParamsRef = true;
    });
  }
}
