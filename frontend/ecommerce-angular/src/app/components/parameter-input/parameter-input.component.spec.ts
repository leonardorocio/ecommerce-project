import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParameterInputComponent } from './parameter-input.component';

describe('ParameterInputComponent', () => {
  let component: ParameterInputComponent;
  let fixture: ComponentFixture<ParameterInputComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParameterInputComponent]
    });
    fixture = TestBed.createComponent(ParameterInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
