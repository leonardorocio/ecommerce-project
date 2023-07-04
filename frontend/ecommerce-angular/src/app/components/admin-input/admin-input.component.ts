import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import {
  APIDocs,
  MethodProperties,
  Parameters,
  PropertyAttributes,
  SchemaProperties,
  Tag,
} from 'src/app/models/admin';
import { ApiDocsService } from 'src/app/services/api-docs.service';

@Component({
  selector: 'app-admin-input',
  templateUrl: './admin-input.component.html',
  styleUrls: ['./admin-input.component.css'],
})
export class AdminInputComponent {
  constructor(private apiDocs: ApiDocsService, private toastr: ToastrService) {}

  @Input() method!: MethodProperties;
  @Input() docs!: APIDocs;
  @Input() tag!: string;
  requestBody!: SchemaProperties;
  operationReturnData$!: Observable<any>;

  hasRequestBody(method: MethodProperties): boolean {
    const hasRequestBody = Object.keys(method).includes('requestBody');
    if (hasRequestBody) {
      this.requestBody = this.apiDocs.getRequestBody(this.docs, method);
    }
    return hasRequestBody;
  }

  hasParameters(method: MethodProperties): boolean {
    return Object.keys(method).includes('parameters');
  }

  submitForm(operation: string, requestForm: NgForm) {
    const parameters = Object.entries(requestForm.value)
      .filter((field) => field[0].includes('param')).map((field) => field[1]);
    const requestBody = Object.fromEntries(Object.entries(requestForm.value)
      .filter((field) => !field[0].includes('param')));
    const chosenService = this.apiDocs.chooseServiceToCall(this.tag);
    this.operationReturnData$ = this.apiDocs.executeOperation(operation, parameters, requestBody, chosenService)
    this.operationReturnData$.subscribe(() => {
      this.toastr.success(`${operation} concluída com sucesso`, 'OK');
    });
  }


}
