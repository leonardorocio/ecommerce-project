import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminEntityComponent } from './admin-entity.component';

describe('AdminEntityComponent', () => {
  let component: AdminEntityComponent;
  let fixture: ComponentFixture<AdminEntityComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminEntityComponent]
    });
    fixture = TestBed.createComponent(AdminEntityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
