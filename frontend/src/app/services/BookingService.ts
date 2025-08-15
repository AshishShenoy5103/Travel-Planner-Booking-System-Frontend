import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class BookingService {
  private bookingsChanged = new Subject<void>();
  bookingsChanged$ = this.bookingsChanged.asObservable();

  notifyBookingsChanged() {
    this.bookingsChanged.next();
  }
}
