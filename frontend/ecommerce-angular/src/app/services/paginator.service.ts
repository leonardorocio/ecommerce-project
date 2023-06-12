import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { CityResponse } from '../models/address';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class PaginatorService {

  constructor(private errorHandling: ErrorHandlingService, private toastr: ToastrService) { }

  showDropdown: boolean = false;

  showDropdownMenu(option: boolean) {
    this.showDropdown = option;
  }

  changePage(currentPage: number, pageSize: number, items: Array<any>, option: number): Array<any> {
    if (
      currentPage + option < 1 ||
      currentPage * pageSize + option > items.length
    ) {
      this.toastr.error('Indíce indisponível', 'Erro');
      return [];
    } else {
      currentPage += option;
      let showingItems = items.slice(
        (currentPage - 1) * pageSize,
        pageSize * currentPage
      );
      return showingItems;
    }
  }

}
