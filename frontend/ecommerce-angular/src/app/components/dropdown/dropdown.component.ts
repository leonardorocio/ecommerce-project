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

  items?: Array<any>;

  @Input() show!: boolean;
  @Input() staticItems?: any;
  @Input() propsToShow!: string[];
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

  showItem(item: any): string {
    var showString: string = '';
    if (this.propsToShow.length === 1) {
      showString = item[this.propsToShow[0] as keyof typeof item];
    } else if (this.propsToShow[0] === 'key' && this.propsToShow[1] === 'value') {
      this.propsToShow.forEach((prop) => {
        showString = showString.concat(item[prop as keyof typeof item] + ": ");
      })
    } else {
      this.propsToShow.forEach((prop) => {
        showString = showString.concat(item[prop as keyof typeof item] + " ");
      })
    }
    const index = showString.lastIndexOf(": ");
    return index !== -1 ? showString.substring(0, index) : showString;
  }

  selectItem(item: any) {
    this.selectedItemEvent.emit(item);
  }

  isObject(value: unknown): object | null {
    return typeof value === 'object' ? value: null;
  }
}
