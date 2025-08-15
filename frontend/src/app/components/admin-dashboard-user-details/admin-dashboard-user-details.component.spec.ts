import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDashboardUserDetailsComponent } from './admin-dashboard-user-details.component';

describe('AdminDashboardUserDetailsComponent', () => {
  let component: AdminDashboardUserDetailsComponent;
  let fixture: ComponentFixture<AdminDashboardUserDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDashboardUserDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDashboardUserDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
