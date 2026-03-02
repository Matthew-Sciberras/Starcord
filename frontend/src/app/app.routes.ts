import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { SignupComponent } from './features/auth/signup/signup.component';
import { HomeComponent } from './features/main/home/home';

export const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent },
  {path: 'signup', component: SignupComponent},
  {path: 'home', component: HomeComponent }
];
