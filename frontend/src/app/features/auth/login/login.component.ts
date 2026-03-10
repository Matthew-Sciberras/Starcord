import { Component, inject, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { emailValidator, passwordRequiredValidator, safeTextValidator } from '@shared/validators';
import { firstValueFrom } from 'rxjs';

import { NotificationService } from '@shared/services/notification.service';
import { LoginRequest } from './login-request.model';
import { AuthService } from '@app/core/auth/authentication.service';
import { AuthStateService } from '@app/core/auth/auth-state.service';
import { ChannelService } from '@app/core/channels/channel.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [RouterLink, FormsModule, ReactiveFormsModule],
})
export class LoginComponent {
  constructor(
    private notification: NotificationService,
    private authService: AuthService,
    private authStateService: AuthStateService
  ) {}

  private router = inject(Router);

  showPassword = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  isFocused = {
    email: false,
    password: false,
  };

  loginForm = new FormGroup({
    email: new FormControl<string>('', [Validators.required, emailValidator(), safeTextValidator()]),
    password: new FormControl<string>('', [Validators.required, passwordRequiredValidator(), safeTextValidator()]),
  });

  async onSubmit(): Promise<void> {
    // Mark all controls as touched so errors show if invalid
    this.loginForm.markAllAsTouched();

    if (this.loginForm.invalid) {
      const errorMessages: Record<string, string> = {
        email_required: 'Please enter an email',
        email_invalidEmail: 'Please enter a valid email',
        password_required: 'Please enter a password',
      };

      const errors: string[] = [];

      Object.entries(this.loginForm.controls).forEach(([controlName, control]) => {
        if (!control.errors) return;

        Object.keys(control.errors).forEach((errorKey) => {
          const messageKey = `${controlName}_${errorKey}`;
          errors.push(errorMessages[messageKey] ?? 'Invalid input');
        });
      });

      if (errors.length > 0) {
        this.notification.showError(errors[0]);
      }

      return; // Don't send request
    }

    const { email, password } = this.loginForm.value;
    const loginData: LoginRequest = {
      email: email!,
      password: password!,
    };

    try {
      const response = await firstValueFrom(this.authService.login(loginData));

      console.log('Logged in successfully:', response);

      this.authStateService.setAccessToken(response.accessToken);

      this.notification.showSuccess('Successfully logged in!');
      await this.router.navigateByUrl("/home")
    } catch (err: any) {
      console.error('Login failed:', err);

      if (err.status === 401) {
        this.notification.showError('Email or password invalid.');
        this.loginForm.patchValue({ password: '' });
      } else {
        this.notification.showError('Unknown error, please try again later.');
      }
    }
  }
}
