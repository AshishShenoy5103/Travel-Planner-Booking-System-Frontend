import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  private usersChanged = new Subject<void>();
  usersChanged$ = this.usersChanged.asObservable();

  notifyUsersChanged() {
    this.usersChanged.next();
  }
}
