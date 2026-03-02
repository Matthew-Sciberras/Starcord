import { Component, inject } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthStateService } from '@app/core/auth/auth-state.service';
import { AuthService } from '@app/core/auth/authentication.service';
import { NotificationService } from '@app/shared/services/notification.service';
import {
  emailValidator,
  passwordRequiredValidator,
  passwordStrengthValidator,
} from '@app/shared/validators';
import { safeTextValidator } from '@app/shared/validators/text.validator';
import { SignupRequest } from './signup-request.model';
import { firstValueFrom } from 'rxjs';
import { ApiError } from '@app/shared/models/api-error.model';
import { isApiError } from '@app/shared/utils/api-error.guard';

@Component({
  selector: 'app-signup',
  imports: [RouterLink, FormsModule, ReactiveFormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent {
  constructor(
    private notification: NotificationService,
    private authService: AuthService,
    private authStateService: AuthStateService,
  ) {}

  private router = inject(Router);

  signupForm = new FormGroup({
    email: new FormControl<string>('', [
      Validators.required,
      emailValidator(),
      safeTextValidator(),
    ]),
    displayName: new FormControl<string>('', [safeTextValidator()]),
    username: new FormControl<string>('', [Validators.required, safeTextValidator()]),
    password: new FormControl<string>('', [
      Validators.required,
      passwordRequiredValidator(),
      passwordStrengthValidator(),
      safeTextValidator(),
    ]),
  });

  showPassword = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  isFocused = {
    email: false,
    displayName: false,
    username: false,
    password: false,
  };

  async onSubmit(): Promise<void> {
    this.signupForm.markAllAsTouched();

    if (this.signupForm.invalid) {
      const errorMessages: Record<string, string> = {
        email_required: 'Please enter an email',
        email_invalidEmail: 'Please enter a valid email',
        password_required: 'Please enter a password',
        password_strength: 'Please input a stronger password'
      };

      const errors: string[] = [];

      Object.entries(this.signupForm.controls).forEach(([controlName, control]) => {
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

    const { username, password, email, displayName } = this.signupForm.value;
    const signupData: SignupRequest = {
      username: username!,
      password: password!,
      email: email!,
      displayName: displayName!,
    };

    try {
      const response = await firstValueFrom(this.authService.signup(signupData));
      this.notification.showSuccess('Account created successfully');
      this.router.navigateByUrl("/login")
    } catch (err: unknown) {
      if (isApiError(err)) {
        this.notification.showError(err.message);
      } else {
        this.notification.showError("An Unknown error occoured")
      }
    }
  }
}
