import {
  AfterContentInit,
  AfterViewChecked,
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, delay } from 'rxjs';
import { APIDocs, IMethod, MethodProperties } from 'src/app/models/admin';
import { ApiDocsService } from 'src/app/services/api-docs.service';

@Component({
  selector: 'app-admin-entity',
  templateUrl: './admin-entity.component.html',
  styleUrls: ['./admin-entity.component.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminEntityComponent implements OnInit {
  tag!: string;
  docs!: APIDocs;
  paths!: IMethod[];
  methodsMap!: [string, MethodProperties[]][];
  completedMap: boolean = false;
  selectedOperation: string = '';
  constructor(
    private route: ActivatedRoute,
    public apiDocs: ApiDocsService,
  ) {}

  ngOnInit(): void {
    this.tag = this.route.snapshot.params['type'];
    ({ docs: this.docs } = history.state);
    this.paths = this.apiDocs.filterPaths(this.docs, this.tag.toLowerCase());
    this.methodsMap = this.createMethodsMap();
  }

  createMethodsMap() {
    const methodsMap: Map<string, MethodProperties[]> = new Map<
      string,
      MethodProperties[]
    >();
    const arrayOfHttpMethods = ['get', 'post', 'put', 'patch', 'delete'];
    arrayOfHttpMethods.forEach((http) => methodsMap.set(http, []));
    this.paths.forEach((method) => {
      Object.entries(method).forEach((methodProp) => {
        methodsMap.get(methodProp[0])?.push(methodProp[1]);
      });
    });
    this.completedMap = true;
    return Array.from(methodsMap.entries());
  }

  selectOperationToShow(selected: string) {
    if (this.selectedOperation !== selected) {
      this.selectedOperation = selected;
    } else {
      this.selectedOperation = '';
    }
  }
}
