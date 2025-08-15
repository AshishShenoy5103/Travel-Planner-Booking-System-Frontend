import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "../header/header.component";

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
  selector: 'app-user-profile-destination-view',
  imports: [CommonModule],
  templateUrl: './user-profile-destination-view.component.html',
  styleUrl: './user-profile-destination-view.component.css'
})
export class UserProfileDestinationViewComponent implements OnInit {
  bookingId!: number;
  booking?: Booking;
  token: string | null = null;

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('authToken');
    this.bookingId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.token && this.bookingId) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

      this.http.get<Booking>(`/api/user/booking/${this.bookingId}`, { headers })
        .subscribe({
          next: (data) => this.booking = data,
          error: (err) => console.error('Failed to fetch booking detail', err)
        });
    }
  }

  itineraryItems = [
  {
    time: '5:00 AM',
    description: 'Departure from office',
    icon: '<i class="fas fa-bus"></i>',
    color: '#2563eb' // blue-600
  },
  {
    time: '7:00 AM',
    description: 'Breakfast',
    icon: '<i class="fas fa-utensils"></i>',
    color: '#16a34a' // green-600
  },
  {
    time: '10:00 AM',
    description: 'Tea/Coffee Break',
    icon: '<i class="fas fa-coffee"></i>',
    color: '#fbbf24' // yellow-400
  },
  {
    time: '1:00 PM',
    description: 'Arrival at destination',
    icon: '<i class="fas fa-map-marker-alt"></i>',
    color: '#7c3aed' // purple-600
  },
  {
    time: '1:00 PM',
    description: 'Lunch',
    icon: '<i class="fas fa-hamburger"></i>',
    color: '#dc2626' // red-500
  },
  {
    time: '2:00 PM',
    description: 'Reach destination',
    icon: '<i class="fas fa-flag-checkered"></i>',
    color: '#0f766e' // teal-600
  },
  {
    time: '2:00 PM - 5:00 PM',
    description: 'Sightseeing',
    icon: '<i class="fas fa-binoculars"></i>',
    color: '#4f46e5' // indigo-600
  },
  {
    time: '5:00 PM - 7:00 PM',
    description: 'Shopping Time',
    icon: '<i class="fas fa-shopping-bag"></i>',
    color: '#ca8a04' // yellow-500
  },
  {
    time: '7:00 PM',
    description: 'Departure back to office',
    icon: '<i class="fas fa-home"></i>',
    color: '#b91c1c' // red-600
  }
];

}
