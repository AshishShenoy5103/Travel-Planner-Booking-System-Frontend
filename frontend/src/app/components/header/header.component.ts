import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterModule, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive, RouterModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  toHome() {
    document.getElementById("hero")?.scrollIntoView({behavior:"smooth"});
  }

  toDestinations() {
    document.getElementById("destinations")?.scrollIntoView({behavior:"smooth"});
  }

  toBookings() {
    document.getElementById("bookings")?.scrollIntoView({behavior:"smooth"});
  }

  firstName = '';
  lastName = '';
  initials = '';
  dropdownOpen = false;

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit() {
    const token = localStorage.getItem('authToken');
    console.log(token)
    if(token)  {
      this.http.get<any>('/api/user/me', {headers: {Authorization: `Bearer ${token}`}}).subscribe(profile => {
        console.log(profile)
        this.firstName = profile.firstName;
        this.lastName = profile.lastName;
        this.initials = this.getInitials(this.firstName, this.lastName);

      });
    }
  }

  getInitials(first: string, last: string) {
    return (first.charAt(0) + last.charAt(0)).toUpperCase();
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login/user']);
  }
}
