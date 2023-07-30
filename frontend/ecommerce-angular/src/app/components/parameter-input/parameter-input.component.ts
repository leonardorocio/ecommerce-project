import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { Parameters } from 'src/app/models/admin';
import { ApiDocsService } from 'src/app/services/api-docs.service';

@Component({
  selector: 'app-parameter-input',
  templateUrl: './parameter-input.component.html',
  styleUrls: ['./parameter-input.component.css'],
})
export class ParameterInputComponent implements OnChanges {
  constructor(private apiDocs: ApiDocsService) {}

  @Input() form!: NgForm;
  @Input() parameter!: Parameters;
  @Input() operation!: string;
  @Input() tag!: string;
  @Input() chosenService!: Function;
  @Input() changedRefs!: boolean;
  @Output() chosenParam = new EventEmitter<any>();
  paramMap: Map<string, any[]> = new Map<string, any[]>();
  paramList!: any[];
  selectedParamModel?: any;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.parameter.in === 'path' && this.parameter.name.includes('id')) {
      let operation = this.tag.at(0)?.toUpperCase() + this.tag.substring(1);
      operation = operation.endsWith('s') ? operation : operation + 's';
      this.apiDocs
        .executeOperation(
          this.getFetchOperationNameFromRefOrTag(operation),
          [],
          {},
          this.chosenService
        )
        .subscribe((data) => {
          this.paramMap.set(this.parameter.name, data);
          this.getParamRef();
        });
    }
  }

  getFetchOperationNameFromRefOrTag(ref: string): string {
    let returnRef: string;
    switch (ref) {
      case 'ProductCategorys':
      case 'Categorys':
        returnRef = 'ProductCategories';
        break;
      case 'Address':
        returnRef = 'Addresses';
        break;
      case 'Details':
        returnRef = 'OrderDetails';
        break;
      default:
        returnRef = ref;
        break;
    }
    return 'get' + returnRef;
  }

  setSelectedParamModel(param: any) {
    this.chosenParam.emit((param as NgModel).value);
  }

  setFormControl(model: NgModel) {
    this.form.addControl(model);
  }

  getParamRef() {
    const paramList = this.paramMap.get(this.parameter.name) ?? [];
    paramList.sort((a, b) => a.id - b.id);
    this.paramList = paramList;
  }
}
