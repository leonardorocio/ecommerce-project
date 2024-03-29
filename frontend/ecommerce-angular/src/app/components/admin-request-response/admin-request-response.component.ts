import { KeyValue } from '@angular/common';
import {
  AfterViewInit,
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { Observable, tap } from 'rxjs';

@Component({
  selector: 'app-admin-request-response',
  templateUrl: './admin-request-response.component.html',
  styleUrls: ['./admin-request-response.component.css'],
})
export class AdminRequestResponseComponent implements OnChanges {
  @Input() operationReturnData!: any;
  dropDownShow: boolean = false;
  selected: number = -1;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['operationReturnData'].previousValue !== changes['operationReturnData'].currentValue) {
      this.operationReturnData = changes['operationReturnData'].currentValue;
    }
  }

  isString(property: unknown): string | undefined {
    return typeof property === 'string' ? property : undefined;
  }

  isObject(value: unknown): object | null {
    return typeof value === 'object' ? value : null;
  }

  selectObjectToShow(objectIndex: number) {
    if (objectIndex !== this.selected) {
      this.selected = objectIndex;
      this.dropDownShow = true;
    } else {
      this.selected = -1;
      this.dropDownShow = false;
    }
  }

  originalOrder = (
    a: KeyValue<string, any>,
    b: KeyValue<string, any>
  ): number => {
    return 0;
  };
}
