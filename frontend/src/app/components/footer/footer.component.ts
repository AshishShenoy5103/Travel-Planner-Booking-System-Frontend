import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-footer',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  email = 'voyago@gmail.com'

  toHome() {
    document.getElementById("hero")?.scrollIntoView({behavior:"smooth"});
  }

  toDestinations() {
    document.getElementById("destinations")?.scrollIntoView({behavior:"smooth"});
  }

  toBookings() {
    document.getElementById("bookings")?.scrollIntoView({behavior:"smooth"});
  }
}
