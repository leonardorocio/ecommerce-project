import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Observable, map, switchMap } from 'rxjs';
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

  getRefs(requestBody: SchemaProperties) {
    let [ref, value] = Object.entries(requestBody.properties).filter(
      (field) => field[1].$ref !== undefined && field[1].$ref !== null
    )[0];
    value.$ref = value.$ref.split('/')[value.$ref.length - 1];
    const chosenService = this.apiDocs.chooseServiceToCall(
      value.$ref.toLowerCase()
    );
    value.$ref = value.$ref.endsWith('s') ? value.$ref : value.$ref + 's';
    this.apiDocs.executeOperation(value.$ref, [], {}, chosenService);
  }

  hasParameters(method: MethodProperties): boolean {
    return Object.keys(method).includes('parameters');
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
      console.log(data);
      this.toastr.success(`${operation} concluída com sucesso`, 'OK');
    });
  }
}
