import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { CityResponse } from '../models/address';
import { ToastrService } from 'ngx-toastr';
import { Paginator } from '../models/paginator';

@Injectable({
  providedIn: 'root',
})
export class PaginatorService {
  constructor(private toastr: ToastrService) {}

  updateShowingItems = new Subject<Array<any>>();

  updateItems(items: Array<any>) {
    this.updateShowingItems.next(items);
  }

  changePage(paginator: Paginator, option: number): Paginator {
    const pageNumber = Math.ceil((paginator.items.length / paginator.pageSize));
    if (
      paginator.currentPage + option < 1 ||
      paginator.currentPage + option > pageNumber
    ) {
      this.toastr.error('Indíce indisponível', 'Erro');
    } else {
      paginator.currentPage += option;
    }
    paginator.items = paginator.items.slice(
      (paginator.currentPage - 1) * paginator.pageSize,
      paginator.pageSize * paginator.currentPage
    );
    return paginator;
  }
}
