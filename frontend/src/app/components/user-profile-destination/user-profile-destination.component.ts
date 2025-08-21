import { Component } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive, RouterModule, Router } from '@angular/router';

interface Booking {
  bookingId: number;
  destination: string;
  rate: number;
  bookingDate: string;
  numberOfPeople: number;
  createdAt: string;
  status: string;
}

@Component({
  selector: 'app-user-profile-destination',
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive, RouterModule],
  templateUrl: './user-profile-destination.component.html',
  styleUrl: './user-profile-destination.component.css'
})
export class UserProfileDestinationComponent {
  bookings: Booking[] = [];
  token: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.token = localStorage.getItem('authToken');
    this.fetchBookings();
  }

  fetchBookings() {
    if (!this.token) {
      console.error('No auth token found');
      return;
    }

    this.http.get<Booking[]>('/api/user/booking/me', {headers: {Authorization: `Bearer ${this.token}`}}).subscribe({
      next: (data) => {
        console.log('Bookings fetched successfully', data);
        this.bookings = data;
      },
      error: (err) => {
        console.error('Error fetching bookings', err);
      }
    });
  }

  getImageForDestination(destination: string): string {
      const map: Record<string, string> = {
      'Ooty': 'assets/trips/ooty.jpg',
      'Mysore': 'assets/trips/mysuru.png',
      'Shimoga': 'assets/trips/shimoga.png',
      'Goa': 'assets/trips/goa.jpg',
      };
    return map[destination];
  }

  canCancel(status: string) {
    return status.toUpperCase() === 'PENDING';
  }

  showAlert(message: string) {
    alert(message);
  }

  confirmCancel(id: number, destination: string) {
    if (!this.token) {
      alert('You are not logged in!');
      return;
    }

    if (confirm(`Are you sure you want to cancel the booking for ${destination}?`)) {

      // this.http.delete<{ message: string }>(`/api/user/booking/${id}`, {headers: {Authorization: `Bearer ${this.token}`}}).subscribe({
      //   next: (res) => {
      //     alert(res.message);
      //     this.fetchBookings();
      //   },
      //   error: (err) => {
      //     console.error(err);
      //     alert('Failed to cancel booking. Please try again.');
      //   }
      // });

      this.http.put<{ message: string }>(`/api/user/booking/${id}/cancel`, {}, { headers: { Authorization: `Bearer ${this.token}` } }).subscribe({
        next: (res) => {
          alert('Booking cancelled successfully');
          this.fetchBookings();
        },
        error: (err) => {
          console.error(err);
          alert('Failed to cancel booking. Please try again.');
        }
      });
    }
  }
}
