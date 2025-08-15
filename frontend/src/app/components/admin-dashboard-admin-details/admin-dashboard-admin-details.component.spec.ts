import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDashboardAdminDetailsComponent } from './admin-dashboard-admin-details.component';

describe('AdminDashboardAdminDetailsComponent', () => {
  let component: AdminDashboardAdminDetailsComponent;
  let fixture: ComponentFixture<AdminDashboardAdminDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDashboardAdminDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDashboardAdminDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
