import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard-header',
  imports: [],
  templateUrl: './admin-dashboard-header.component.html',
  styleUrl: './admin-dashboard-header.component.css'
})
export class AdminDashboardHeaderComponent {
  constructor(private router: Router) {}

  logout() {
    localStorage.clear();
    this.router.navigate(['/login/admin']);
  }
}
