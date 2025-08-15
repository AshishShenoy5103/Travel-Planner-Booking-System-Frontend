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

export const routes: Routes = [
  {path: '', redirectTo: 'login/user', pathMatch: 'full' },
  {path: 'home', component: HomeComponent},
  {path: 'faq', component: FaqLayoutComponent},
  {path: 'login/user', component: UserLoginComponent},
  {path: 'register/user', component: UserRegisterComponent},
  {path: 'login/admin', component: AdminLoginComponent},
  {path: 'admin/dashboard', component: AdminDashboardLayoutComponent},
  {path: 'user/profile', component: UserProfileLayoutComponent},
  {path: 'user/profile/view/:id', component: UserProfileDestinationViewLayoutComponent},
  {path: '**', component: PageNotFoundComponent}
];
