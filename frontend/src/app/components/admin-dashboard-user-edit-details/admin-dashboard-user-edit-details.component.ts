import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/UserService';

@Component({
  selector: 'app-admin-dashboard-user-edit-details',
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard-user-edit-details.component.html',
  styleUrl: './admin-dashboard-user-edit-details.component.css'
})
export class AdminDashboardUserEditDetailsComponent {
  searchUserId: number | null = null;
  user: any = null;
  originalUser: any = null; // backup for cancel
  editMode = false;
  searchClicked = false;

  constructor(private http: HttpClient, private userService: UserService) {}

  fetchUser() {
    if (!this.searchUserId) return;

    this.searchClicked = true;
    const token = localStorage.getItem('authToken');

    this.http.get(`/api/admin/user/${this.searchUserId}`, { headers: { Authorization: `Bearer ${token}` } })
      .subscribe({
        next: (data) => {
          this.user = { ...data }; // clone for editing
          this.originalUser = { ...data }; // backup original
          this.editMode = false;
        },
        error: () => {
          this.user = null;
        }
      });
  }

  onEdit() {
    this.editMode = true;
  }

  onCancel() {
    this.editMode = false;
    if (this.originalUser) {
      this.user = { ...this.originalUser }; // restore exact original data
    }
  }

  onSave() {
    if (!this.user || !this.user.userId) return;

    const token = localStorage.getItem('authToken');

    const body = {
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      phoneNumber: this.user.phoneNumber,
      city: this.user.city,
      userType: this.user.userType,
      aadharNumber: this.user.aadharNumber,
      createdAt: this.user.createdAt
    };

    this.http.put(`/api/admin/user/${this.user.userId}`, body, {
      headers: new HttpHeaders({ Authorization: `Bearer ${token}` })
    }).subscribe({
      next: (res) => {
        console.log('User updated successfully', res);
        this.editMode = false;
        this.originalUser = { ...this.user }; // update backup after save
      },
      error: (err) => {
        console.error('Error updating user:', err);
      }
    });
  }

  onDelete() {
    if (confirm('Are you sure you want to delete your profile? This action cannot be undone.')) {
      const token = localStorage.getItem('authToken');

      if (token) {
        this.http.delete<{ message: string }>(`/api/admin/user/${this.user.userId}`, { headers: { Authorization: `Bearer ${token}` }}).subscribe({
          next: (res) => {
            alert(res.message);

            this.user = null;
            this.originalUser = null;
            this.searchUserId = null;
            this.searchClicked = false;
            this.editMode = false;

            this.userService.notifyUsersChanged();
          },
          error: (err) => {
            console.error(err);
            alert('Failed to delete profile. Please try again.');
          }
        });
      }
    }
  }
}
