import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import {
  APIDocs,
  MethodProperties,
  PropertyAttributes,
  SchemaProperties,
} from 'src/app/models/admin';
import { ApiDocsService } from 'src/app/services/api-docs.service';

@Component({
  selector: 'app-request-body-input',
  templateUrl: './request-body-input.component.html',
  styleUrls: ['./request-body-input.component.css'],
})
export class RequestBodyInputComponent implements OnChanges, AfterViewInit {

  constructor(private apiDocs: ApiDocsService) {}
  @ViewChild('param', {static: false}) model!: NgModel;

  @Input() form!: NgForm;
  @Input() fieldValue!: any;
  @Input() fieldKey!: string;
  @Input() selectedParamModel: any;
  paramModelValue!: any;
  bodyRef!: any[];

  ngOnChanges(): void {
    this.getRef(this.fieldKey, this.fieldValue);
    this.getAttributeFromSelectedParam();
  }

  ngAfterViewInit(): void {
    this.setFormControl(this.model);
  }

  getAttributeFromSelectedParam() {
    console.log(this.selectedParamModel);
    console.log(this.fieldKey);
    this.paramModelValue = this.selectedParamModel?.[
      this.fieldKey as keyof typeof this.selectedParamModel
    ];
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

  setFormControl(model: NgModel) {
    this.form.addControl(model);
  }

  getRef(key: string, value: PropertyAttributes) {
    if (value.$ref !== undefined && value.$ref !== null) {
      value.$ref = value.$ref.split('/').pop() ?? '';
      value.$ref = value.$ref.endsWith('s') ? value.$ref : value.$ref + 's';
      const chosenService = this.apiDocs.chooseServiceToCall(
        value.$ref.toLowerCase()
      );
      this.apiDocs
        .executeOperation(
          this.getFetchOperationNameFromRefOrTag(value.$ref),
          [],
          {},
          chosenService
        )
        .subscribe((data) => {
          this.bodyRef = data;
        });
    }
  }
}
