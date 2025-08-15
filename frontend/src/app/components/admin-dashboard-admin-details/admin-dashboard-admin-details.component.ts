import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-dashboard-admin-details',
  imports: [CommonModule],
  templateUrl: './admin-dashboard-admin-details.component.html',
  styleUrl: './admin-dashboard-admin-details.component.css'
})
export class AdminDashboardAdminDetailsComponent implements OnInit {
  admins: any[] = [];
  selectedAdmin: any = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    const token = localStorage.getItem('authToken');
    this.http.get<any[]>('/api/admin/admins', { headers: { Authorization: `Bearer ${token}`}}).subscribe({
      next: (data) => (this.admins = data),
      error: (err) => console.error('Error fetching admins:', err),
    });
  }

  viewAdmin(admin: any) {
    this.selectedAdmin = admin;
  }

  closeModal() {
    this.selectedAdmin = null;
  }
}
