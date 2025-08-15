import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDashboardUserBookingEditComponent } from './admin-dashboard-user-booking-edit.component';

describe('AdminDashboardUserBookingEditComponent', () => {
  let component: AdminDashboardUserBookingEditComponent;
  let fixture: ComponentFixture<AdminDashboardUserBookingEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDashboardUserBookingEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDashboardUserBookingEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
