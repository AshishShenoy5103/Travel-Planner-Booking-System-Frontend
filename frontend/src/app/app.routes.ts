import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { FaqLayoutComponent } from './components/faq-layout/faq-layout.component';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { UserRegisterComponent } from './components/user-register/user-register.component';
import { AdminLoginComponent } from './components/admin-login/admin-login.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { UserProfileLayoutComponent } from './components/user-profile-layout/user-profile-layout.component';
import { UserProfileDestinationViewLayoutComponent } from './components/user-profile-destination-view-layout/user-profile-destination-view-layout.component';
import { AdminDashboardLayoutComponent } from './components/admin-dashboard-layout/admin-dashboard-layout.component';
import { AuthGuard } from './auth.guard';

export const routes: Routes = [
  {path: '', redirectTo: 'login/user', pathMatch: 'full' },

  // Public routes
  {path: 'login/user', component: UserLoginComponent},
  {path: 'register/user', component: UserRegisterComponent},
  {path: 'login/admin', component: AdminLoginComponent},

  // Protected routes
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'faq', component: FaqLayoutComponent, canActivate: [AuthGuard]},
  {path: 'admin/dashboard', component: AdminDashboardLayoutComponent, canActivate: [AuthGuard]},
  {path: 'user/profile', component: UserProfileLayoutComponent, canActivate: [AuthGuard]},
  {path: 'user/profile/view/:id', component: UserProfileDestinationViewLayoutComponent, canActivate: [AuthGuard]},

  // Catch-all route for 404 Not Found
  {path: '**', component: PageNotFoundComponent}
];
