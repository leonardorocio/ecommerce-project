import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { CityResponse } from 'src/app/models/address';
import { PaginatorService } from 'src/app/services/paginator.service';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.css'],
})
export class DropdownComponent implements OnInit {
  constructor(private paginatorService: PaginatorService) {}

  items!: Array<any>;

  @Input() staticItems?: Array<any>;
  @Output() selectedItemEvent = new EventEmitter<any>();

  ngOnInit(): void {
    if (!this.staticItems?.length) {
      this.paginatorService.updateShowingItems.subscribe((items) => {
        this.items = items;
      });
    } else {
      this.items = this.staticItems;
    }
  }

  selectItem(item: any) {
    this.selectedItemEvent.emit(item);
  }
}
