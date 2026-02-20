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

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [RouterLink, FormsModule, ReactiveFormsModule],
})
export class LoginComponent {
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
      const emailErrors = this.loginForm.controls.email.errors;
      const passwordErrors = this.loginForm.controls.password.errors;
      const errorMessages = {
        emailRequired: 'Please enter an email',
        passwordRequired: ' Please enter a password',
        invalidEmail: 'Please enter a valid email',
        passwordStrength: 'Please enter a strong password',
        invalidPassword: 'Password is invalid',
      };
      console.log('Form is invalid', this.loginForm.errors, this.loginForm.controls);
      console.log('Email errors: ', emailErrors);
      console.log('Password errors: ', passwordErrors);

      var errors: String[] = [];
      if (emailErrors != null) {
        Object.keys(emailErrors).forEach((key) => {
          switch (key) {
            case 'required':
              errors.push(errorMessages.emailRequired);
              break;
            case 'invalidEmail':
              errors.push(errorMessages.invalidEmail);
              break;
            default:
              errors.push(errorMessages.invalidEmail);
              break;
          }
        });
      }

      if (passwordErrors != null) {
        Object.keys(passwordErrors).forEach((key) => {
          switch (key) {
            case 'required':
              errors.push(errorMessages.passwordRequired);
              break;
            case 'passwordStrength':
              errors.push(errorMessages.passwordStrength);
              break;
            default:
              errors.push(errorMessages.invalidPassword);
              break;
          }
        });
      }

      console.log(JSON.stringify(errors));
      return;
    }

    const { email, password } = this.loginForm.value;
    console.log('Form submitted:', { email, password });
    // Call your login API here
  }
}
