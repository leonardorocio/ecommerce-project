import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRequestResponseComponent } from './admin-request-response.component';

describe('AdminRequestResponseComponent', () => {
  let component: AdminRequestResponseComponent;
  let fixture: ComponentFixture<AdminRequestResponseComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminRequestResponseComponent]
    });
    fixture = TestBed.createComponent(AdminRequestResponseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
