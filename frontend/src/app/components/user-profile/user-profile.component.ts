import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule, FormsModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {
  firstName = '';
  lastName = '';
  initials = '';
  email = '';
  phoneNumber = '';
  city = '';
  aadharNumber = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    const token = localStorage.getItem('authToken');
    console.log(token)
    if(token)  {
      this.http.get<any>('/api/user/me', {headers: {Authorization: `Bearer ${token}`}}).subscribe(profile => {
        console.log(profile)
        this.firstName = profile.firstName;
        this.lastName = profile.lastName;
        this.initials = this.getInitials(this.firstName, this.lastName);
        this.email = profile.email;
        this.phoneNumber = profile.phoneNumber;
        this.city = profile.city;
        this.aadharNumber = profile.aadharNumber;
      });
    }
  }

  getInitials(first: string, last: string) {
    return (first.charAt(0) + last.charAt(0)).toUpperCase();
  }

  editMode = false;
  private originalPhoneNumber = this.phoneNumber;
  private originalCity = this.city;

  onEdit() {
    this.editMode = true;
    this.originalPhoneNumber = this.phoneNumber;
    this.originalCity = this.city;
  }

  onCancel() {
    this.editMode = false;
    this.phoneNumber = this.originalPhoneNumber;
    this.city = this.originalCity;
  }

  onSave() {
    const updatedUser = {
      email: this.email,
      firstName: this.firstName,
      lastName: this.lastName,
      aadharNumber: this.aadharNumber,
      city: this.city,
      phoneNumber: this.phoneNumber
    };
    const token = localStorage.getItem('authToken');

    if (token) {
      this.http.put<any>('/api/user/me', updatedUser, { headers: { Authorization: `Bearer ${token}`}}).subscribe({
        next: (res) => {
          this.firstName = res.firstName;
          this.lastName = res.lastName;
          this.email = res.email;
          this.phoneNumber = res.phoneNumber;
          this.city = res.city;
          this.aadharNumber = res.aadharNumber;

          alert('Profile updated successfully.');
          this.editMode = false;
        },
        error: (err) => {
          console.error(err);
          alert('Failed to update profile. Please try again.');
        }
      });
    }
  }

  onDelete() {
    if (confirm('Are you sure you want to delete your profile? This action cannot be undone.')) {
      const token = localStorage.getItem('authToken');

      if (token) {
        this.http.delete<{ message: string }>('/api/user/me', { headers: { Authorization: `Bearer ${token}` }}).subscribe({
          next: (res) => {
            alert(res.message);
            localStorage.removeItem('authToken');
            this.router.navigate(['/']);
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
