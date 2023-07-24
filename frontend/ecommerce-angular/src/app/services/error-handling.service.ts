import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlingService {
  constructor(private toastr: ToastrService, private router: Router) {}

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
      const err: HttpErrorResponse = error as HttpErrorResponse;
      if (
        !err.url?.includes('auth') &&
        err.status === HttpStatusCode.Unauthorized
      ) {
        Swal.fire({
          title: 'Erro: Usuário não autenticado',
          text: 'Deseja fazer login?',
          icon: 'error',
          showCancelButton: true,
          confirmButtonColor: '#ff0000',
          confirmButtonText: 'Confirmar',
        }).then((result) => {
          if (result.value) {
            this.router.navigateByUrl('/auth');
          } else {
            this.router.navigateByUrl('/dashboard');
          }
        });
      } else {
        if (error.error['fields']) {
          this.toastr.error(this.getFieldsErrors(error), `${operation}: ${error.error['title']}`);
        } else {
          console.log("Test");
          this.toastr.error(error.error['details'], `${operation}: ${error.error['title']}`);
        }
      }

      return of(result as T);
    };
  }
}
