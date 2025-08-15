import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-login',
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent {
  email = '';
  password = '';
  errorMessage = '';

  isLoading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.login({email: this.email, password: this.password}).subscribe({
      next: () => {
        this.router.navigate(['/home']);
      },
      error: (err) => {
        if (err.status === 401 && err.error?.error) {
          alert(err.error.error); // shows "Invalid email or password"
        } else {
          alert("Something went wrong");
        }
      }
    })
  }
}
