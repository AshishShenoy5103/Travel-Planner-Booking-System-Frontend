import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/UserService';

@Component({
  selector: 'app-admin-dashboard-user-details',
  imports: [CommonModule],
  templateUrl: './admin-dashboard-user-details.component.html',
  styleUrl: './admin-dashboard-user-details.component.css'
})
export class AdminDashboardUserDetailsComponent implements OnInit {
  users: any[] = [];
  selectedUser: any = null;

  constructor(private http: HttpClient, private userService: UserService) {}

  ngOnInit() {
    this.fetchUsers();

    this.userService.usersChanged$.subscribe(() => {
      this.fetchUsers();
    });
  }

  fetchUsers() {
    const token = localStorage.getItem('authToken');
    this.http.get<any[]>('api/admin/users', { headers: { Authorization: `Bearer ${token}`}}).subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.error('Error fetching users:', err);
      }
    });
  }

  viewUser(user: any) {
    this.selectedUser = user;
  }

  closeModal() {
    this.selectedUser = null;
  }
}
