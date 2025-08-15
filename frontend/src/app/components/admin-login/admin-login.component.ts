import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthAdminService } from '../../services/authAdmin.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-login',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './admin-login.component.html',
  styleUrl: './admin-login.component.css'
})
export class AdminLoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authAdminService: AuthAdminService, private router: Router) {}

  onSubmit() {
    console.log(this.email)
    console.log(this.password)
    this.authAdminService.login({email: this.email, password: this.password}).subscribe({
      next: () => {
        this.router.navigate(['/admin/dashboard']);
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
