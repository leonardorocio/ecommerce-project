import { Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlingService {

  constructor(private toastr: ToastrService) { }

  private getFieldsErrors(error: any): string {
    const fields: string[] = error.error['fields'].split(',');
    const fieldMessage: string[] = error.error['fieldsMessage'].split(',');
    let errorMessage: string = '';
    for (let i = 0; i < fields.length; i++) {
      errorMessage += `${fields[i]}: ${fieldMessage}\n`;
    }
    return errorMessage;
  }

  public handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);

      if (error.error['fields']) {
        this.toastr.error(this.getFieldsErrors(error), error.error['title']);
      } else {
        this.toastr.error(error.error['details'], error.error['title']);
      }

      return of(result as T);
    };
  }
}
