import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login';

export const routes: Routes = [
  {path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent },
];
