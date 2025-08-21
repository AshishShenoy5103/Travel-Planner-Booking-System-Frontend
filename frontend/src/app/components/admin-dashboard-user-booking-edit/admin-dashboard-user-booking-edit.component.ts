import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BookingService } from '../../services/BookingService';

@Component({
  selector: 'app-admin-dashboard-user-booking-edit',
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard-user-booking-edit.component.html',
  styleUrl: './admin-dashboard-user-booking-edit.component.css'
})
export class AdminDashboardUserBookingEditComponent {
  searchBookingId: number | null = null;
  booking: any = null;
  searchClicked = false;
  editMode = false;
  allowedStatuses: string[] = [];

  constructor(private http: HttpClient, private bookingService:BookingService) {}

  fetchBooking() {
    this.searchClicked = true;
    if (!this.searchBookingId) return;

    const token = localStorage.getItem('authToken');
    this.http.get(`/api/admin/user/booking/${this.searchBookingId}`, {
      headers: { Authorization: `Bearer ${token}` }
    }).subscribe({
      next: (data: any) => {
        this.booking = data;
        this.setAllowedStatuses();
      },
      error: () => {
        this.booking = null;
      }
    });
  }

  setAllowedStatuses() {
    if (!this.booking) return;
    const status = this.booking.status.toUpperCase();
    if (status === 'PENDING') {
      this.allowedStatuses = ['PENDING', 'CONFIRMED', 'CANCELLED'];
    } else if (status === 'CONFIRMED') {
      this.allowedStatuses = ['CONFIRMED', 'COMPLETED', 'CANCELLED'];
    } else {
      this.allowedStatuses = [status]; // No changes allowed
    }
  }

  onEdit() {
    this.editMode = true;
  }

  onCancel() {
    this.editMode = false;
    this.fetchBooking(); // Reset changes
  }

  onSave() {
    if (!this.booking) return;
    const token = localStorage.getItem('authToken');
    this.http.put(`/api/admin/user/booking/${this.booking.bookingId}`, this.booking, {
      headers: { Authorization: `Bearer ${token}` }
    }).subscribe({
      next: () => {
        this.editMode = false;
        this.bookingService.notifyBookingsChanged();
        this.fetchBooking(); // Refresh data
      }
    });
  }
}
