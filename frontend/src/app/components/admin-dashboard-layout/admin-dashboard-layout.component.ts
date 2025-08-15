import { Component } from '@angular/core';
import { AdminDashboardComponent } from "../admin-dashboard/admin-dashboard.component";
import { AdminDashboardHeaderComponent } from "../admin-dashboard-header/admin-dashboard-header.component";
import { AdminDashboardUserDetailsComponent } from "../admin-dashboard-user-details/admin-dashboard-user-details.component";
import { AdminDashboardAdminDetailsComponent } from "../admin-dashboard-admin-details/admin-dashboard-admin-details.component";
import { AdminDashboardUserEditDetailsComponent } from "../admin-dashboard-user-edit-details/admin-dashboard-user-edit-details.component";
import { AdminDashboardUserBookingComponent } from "../admin-dashboard-user-booking/admin-dashboard-user-booking.component";
import { AdminDashboardUserBookingEditComponent } from "../admin-dashboard-user-booking-edit/admin-dashboard-user-booking-edit.component";

@Component({
  selector: 'app-admin-dashboard-layout',
  imports: [AdminDashboardComponent, AdminDashboardHeaderComponent, AdminDashboardUserDetailsComponent, AdminDashboardAdminDetailsComponent, AdminDashboardUserEditDetailsComponent, AdminDashboardUserBookingComponent, AdminDashboardUserBookingEditComponent],
  templateUrl: './admin-dashboard-layout.component.html',
  styleUrl: './admin-dashboard-layout.component.css'
})
export class AdminDashboardLayoutComponent {

}
