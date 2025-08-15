import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDashboardUserEditDetailsComponent } from './admin-dashboard-user-edit-details.component';

describe('AdminDashboardUserEditDetailsComponent', () => {
  let component: AdminDashboardUserEditDetailsComponent;
  let fixture: ComponentFixture<AdminDashboardUserEditDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDashboardUserEditDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDashboardUserEditDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
