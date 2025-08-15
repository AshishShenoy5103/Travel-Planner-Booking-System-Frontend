import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-register',
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './user-register.component.html',
  styleUrl: './user-register.component.css'
})
export class UserRegisterComponent {
  firstName: string = '';
  lastName: string = '';
  email: string = '';
  password: string = '';
  aadharNumber: string = '';
  phoneNumber: string = '';
  city: string = '';

    constructor(private authService: AuthService, private router: Router) {}

  onSubmit(form: any) {
    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }

    const userData = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password,
      aadharNumber: this.aadharNumber,
      phoneNumber: this.phoneNumber,
      city: this.city
    };


    this.authService.register(userData).subscribe({
      next: (response) => {
        alert('Registration successful!');
        this.router.navigate(['/login/user']);
      },
      error: (err) => {
        console.error(err);
        alert(err.error?.message || 'Registration failed! Please try again.');
      }
    });
  }
}
