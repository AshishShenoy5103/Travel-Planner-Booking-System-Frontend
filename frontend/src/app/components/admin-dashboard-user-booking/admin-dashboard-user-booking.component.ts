import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BookingService } from '../../services/BookingService';
import { UserService } from '../../services/UserService';

@Component({
  selector: 'app-admin-dashboard-user-booking',
  imports: [CommonModule],
  templateUrl: './admin-dashboard-user-booking.component.html',
  styleUrl: './admin-dashboard-user-booking.component.css'
})
export class AdminDashboardUserBookingComponent implements OnInit {
  bookings: any[] = [];
  selectedBooking: any = null;

  constructor(private http: HttpClient, private bookingService: BookingService, private userService: UserService) { }

  ngOnInit(): void {
    this.loadBookings();

    this.bookingService.bookingsChanged$.subscribe(() => {
      this.loadBookings();
    });

    this.userService.usersChanged$.subscribe(() => {
      this.loadBookings();
    });
  }

  loadBookings(): void {
    const token = localStorage.getItem('authToken');
    this.http.get<any[]>('/api/admin/users/bookings', { headers: { Authorization: `Bearer ${token}` } }).subscribe({
      next: (data) => {
        this.bookings = data.map(b => ({
          bookingId: b.bookingId,
          userId: b.userId,
          destination: b.destination,
          status: b.status
        }));
      },
      error: (err) => {
        console.error('Error fetching bookings:', err);
      }
    });
  }

  viewBooking(bookingId: number) {
    const token = localStorage.getItem('authToken');
    this.http.get<any>(`/api/admin/user/booking/${bookingId}`, {
      headers: { Authorization: `Bearer ${token}` }
    }).subscribe({
      next: (data) => {
        this.selectedBooking = data;
      },
      error: (err) => {
        console.error('Error fetching booking details:', err);
      }
    });
  }

  closeBookingModal() {
    this.selectedBooking = null;
  }
}
