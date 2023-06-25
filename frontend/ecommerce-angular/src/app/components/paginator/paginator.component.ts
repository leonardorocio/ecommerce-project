import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChange,
  SimpleChanges,
  TemplateRef,
} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { CityResponse } from 'src/app/models/address';
import { Paginator } from 'src/app/models/paginator';
import { AddressService } from 'src/app/services/address.service';
import { PaginatorService } from 'src/app/services/paginator.service';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css'],
})
export class PaginatorComponent implements OnChanges {

  @Input() items: Array<any> = [];
  @Input() pageSize = 5;
  showingItems: Array<any> = [...this.items];
  currentPage: number = 0;

  constructor(
    public paginatorService: PaginatorService,
    ) {}

    ngOnChanges(changes: SimpleChanges): void {
      if (changes['items']) {
        this.items = changes['items'].currentValue;
        this.currentPage = 0;
        this.changePage(1);
      }
    }

  changePage(option: number) {
    const paginator: Paginator = {
      currentPage: this.currentPage,
      pageSize: this.pageSize,
      items: this.items
    }
    const newPaginator = this.paginatorService.changePage(paginator, option);
    ({currentPage: this.currentPage, pageSize: this.pageSize, items: this.showingItems} = newPaginator);
    this.paginatorService.updateItems(this.showingItems);
  }
}
