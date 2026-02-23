import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { RouterLink } from '@angular/router';
import {
  emailValidator,
  passwordRequiredValidator
} from '@shared/validators';

import { NotificationService } from '@shared/services/notification.service';
import { LoginRequest } from './login-request.model';
import { AuthService } from '@app/shared/services/authentication.service';

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
  ) {}

  showPassword = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  isFocused = {
    email: false,
    password: false,
  };

  loginForm = new FormGroup({
    email: new FormControl<string>('', [Validators.required, emailValidator()]),
    password: new FormControl<string>('', [Validators.required, passwordRequiredValidator()]),
  });

  onSubmit(): void {
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

      console.log(errors);
      return;
    }

    const { email, password } = this.loginForm.value;
    const loginData: LoginRequest = {
      email: email!,
      password: password!,
    };

    this.authService.login(loginData).subscribe({
      next: (response) => {
        console.log('Logged in successfully:', response);
        this.notification.showSuccess('Successfully logged in!');
      },
      error: (err) => {
        console.error('Login failed:', err);
        this.notification.showError('Login failed, please try again.');
      },
    });
    console.log('Form submitted:', { email, password });
    this.notification.showSuccess('Succesfully logged in! ');
  }
}
