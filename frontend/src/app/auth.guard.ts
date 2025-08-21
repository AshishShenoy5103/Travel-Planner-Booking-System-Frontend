import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('authToken');

    if (token) {
      // User is logged in
      return true;
    } else {
      // User not logged in
      this.router.navigate(['/login/user']);
      return false;
    }
  }
}
