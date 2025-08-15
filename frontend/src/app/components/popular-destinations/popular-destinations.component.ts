import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-popular-destinations',
  imports: [CommonModule],
  templateUrl: './popular-destinations.component.html',
  styleUrl: './popular-destinations.component.css'
})
export class PopularDestinationsComponent {
  destinations = [
    {
      title: 'Goa',
      price: 18000,
      image: 'assets/trips/goa.jpg'
    },
    {
      title: 'Mysore',
      price: 12000,
      image: 'assets/trips/mysuru.png'
    },
    {
      title: 'Ooty',
      price: 15000,
      image: 'assets/trips/ooty.jpg'
    },
    {
      title: 'Shimoga',
      price: 10000,
      image: 'assets/trips/shimoga.png'
    }
  ];
}
