import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDashboardUserBookingComponent } from './admin-dashboard-user-booking.component';

describe('AdminDashboardUserBookingComponent', () => {
  let component: AdminDashboardUserBookingComponent;
  let fixture: ComponentFixture<AdminDashboardUserBookingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDashboardUserBookingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDashboardUserBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
