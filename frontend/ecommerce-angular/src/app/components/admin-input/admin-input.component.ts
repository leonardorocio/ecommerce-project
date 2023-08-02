import { KeyValue } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Observable, filter, share, map } from 'rxjs';
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
  operationReturnData!: any;
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

  getParamFromForm(form: object) {
    return Object.entries(form)
      .filter((field) => field[0].includes('param'))
      .map((field) =>
        typeof field[1] === 'object' ? (field[1] as any)?.id : field[1]
      );
  }

  getRequestBodyFromForm(form: object) {
    return Object.fromEntries(
      Object.entries(form)
        .filter((field) => !field[0].includes('param'))
        .map((field) => {
          if (typeof field[1] === 'object') {
            field[1] = (field[1] as any)?.id;
          }
          return field;
        })
    );
  }

  submitForm(operation: string, requestForm: NgForm) {
    const parameters = this.getParamFromForm(requestForm.value);
    const requestBody = this.getRequestBodyFromForm(requestForm.value);
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
    this.apiDocs
      .executeOperation(operation, parameters, requestBody, chosenService)
      .pipe(
        share(),
        map((data) => {
          if (
            ['null', 'undefined', '[]', '{}'].includes(JSON.stringify(data))
          ) {
            this.toastr.success(`${operation} concluída com sucesso`, 'OK');
            return 'Resposta para requisição não foi encontrada';
          }
          return data;
        })
      )
      .subscribe((data) => {
        this.operationReturnData = data;
        if (Object.keys(data ?? {}).length > 0) {
          this.toastr.success(`${operation} concluída com sucesso`, 'OK');
        }
        this.changeParamsRef = !this.changeParamsRef;
      });
  }
}
