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
  passwordStrengthValidator,
  matchFieldsValidator,
} from '@shared/validators';

import { NotificationService } from '@shared/services/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [RouterLink, FormsModule, ReactiveFormsModule],
})
export class LoginComponent {
  constructor(private notification: NotificationService) {}

  showPassword = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  isFocused = {
    email: false,
    password: false,
  };

  loginForm = new FormGroup({
    email: new FormControl<String>('', [Validators.required, emailValidator()]),
    password: new FormControl<String>('', [Validators.required, passwordStrengthValidator()]),
  });

  onSubmit(): void {
    // Mark all controls as touched so errors show if invalid
    this.loginForm.markAllAsTouched();

    if (this.loginForm.invalid) {
      const errorMessages: Record<string, string> = {
        email_required: 'Please enter an email',
        email_invalidEmail: 'Please enter a valid email',
        password_required: 'Please enter a password',
        password_passwordStrength: 'Please enter a strong password',
      };

      const errors: string[] = [];

      Object.entries(this.loginForm.controls).forEach(([controlName, control]) => {
        if (!control.errors) return;

        Object.keys(control.errors).forEach((errorKey) => {
          const messageKey = `${controlName}_${errorKey}`;
          errors.push(errorMessages[messageKey] ?? 'Invalid input');
        });
      });

      if(errors.length > 0) {
        this.notification.showError(errors[0]);
      }

      console.log(errors);
      return;
    }

    const { email, password } = this.loginForm.value;
    console.log('Form submitted:', { email, password });
    this.notification.showSuccess("Succesfully logged in! ")
  }
}
