import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-dashboard',
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {
  firstName = '';
  lastName = '';
  email = '';
  phoneNumber = '';
  city = '';
  aadharNumber = '';
  createdAt: string | null = null;
  initials = '';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    const token = localStorage.getItem('authToken');
    this.http.get<any>('/api/admin/me', { headers: { Authorization: `Bearer ${token}`}}).subscribe({
      next: (data) => {
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.email = data.email;
        this.phoneNumber = data.phoneNumber;
        this.city = data.city;
        this.aadharNumber = data.aadharNumber;
        this.createdAt = data.createdAt;
        this.initials =
          (this.firstName?.charAt(0) || '') + (this.lastName?.charAt(0) || '');
      },
      error: (err) => {
        console.error('Error fetching admin profile:', err);
      }
    });
  }
}
