import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import {
  NgForm,
  NgModel,
  SelectControlValueAccessor,
  Validators,
  ɵNgSelectMultipleOption,
} from '@angular/forms';
import { Observable, share } from 'rxjs';
import { PropertyAttributes } from 'src/app/models/admin';
import { ApiDocsService } from 'src/app/services/api-docs.service';
import { FirebaseStorageService } from 'src/app/services/firebase-storage.service';

@Component({
  selector: 'app-request-body-input',
  templateUrl: './request-body-input.component.html',
  styleUrls: ['./request-body-input.component.css'],
})
export class RequestBodyInputComponent implements OnChanges, AfterViewInit {
  constructor(
    private apiDocs: ApiDocsService,
    private firebase: FirebaseStorageService
  ) {}
  @ViewChild('param', { static: false }) model!: NgModel;
  @ViewChild('select', { static: false }) select!: HTMLSelectElement;

  @Input() form!: NgForm;
  @Input() fieldValue!: any;
  @Input() fieldKey!: string;
  @Input() selectedParamModel: any;
  @Output() disableSubmitButton = new EventEmitter<boolean>();
  paramModelValue!: any;
  paramInsideRef?: any;
  bodyRef!: any[];

  ngOnChanges(changes: SimpleChanges): void {
    this.getRef(this.fieldKey, this.fieldValue);
    this.getAttributeFromSelectedParam();
  }

  ngAfterViewInit(): void {
    this.form.addControl(this.model);
  }

  compareFn(obj1?: any, obj2?: any) {
    return obj1?.id === obj2?.id;
  }

  getAttributeFromSelectedParam() {
    this.paramModelValue =
      this.selectedParamModel?.[
        this.fieldKey as keyof typeof this.selectedParamModel
      ];
    if (typeof this.paramModelValue === 'object') {
      this.paramInsideRef = this.bodyRef.find(
        (ref) => JSON.stringify(ref) === JSON.stringify(this.paramModelValue)
      );
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

  handleFileInput(urlModel: NgModel, fileModel: HTMLInputElement, event: any) {
    if (fileModel.files?.length || !urlModel.value) {
      const file: File = event.target.files[0];
      this.disableSubmitButton.emit(true);
      this.firebase.uploadFile(file).then((url) => {
        this.form.getControl(urlModel).setValue(url);
        this.disableSubmitButton.emit(false);
      });
    } else {
      this.form.getControl(urlModel).setValue(this.paramModelValue);
    }
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
