import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestBodyInputComponent } from './request-body-input.component';

describe('RequestBodyInputComponent', () => {
  let component: RequestBodyInputComponent;
  let fixture: ComponentFixture<RequestBodyInputComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequestBodyInputComponent]
    });
    fixture = TestBed.createComponent(RequestBodyInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
