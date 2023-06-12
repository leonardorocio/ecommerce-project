import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChange,
  SimpleChanges,
} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CityResponse } from 'src/app/models/address';
import { AddressService } from 'src/app/services/address.service';
import { PaginatorService } from 'src/app/services/paginator.service';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css'],
})
export class PaginatorComponent implements OnChanges {
  @Input() items = [] as CityResponse[];
  @Input() pageSize = 10;
  @Input() action!: (city: CityResponse) => void;
  showingItems: CityResponse[] = [...this.items];
  currentPage: number = 0;

  @Output() selectedItemEvent = new EventEmitter<CityResponse>();

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

  selectItem(item: CityResponse) {
    this.selectedItemEvent.emit(item);
  }

  changePage(option: number) {
    const newPageItems = this.paginatorService.changePage(this.currentPage, this.pageSize, this.items, option);
    this.showingItems =  newPageItems ? newPageItems : this.showingItems;
  }
}
