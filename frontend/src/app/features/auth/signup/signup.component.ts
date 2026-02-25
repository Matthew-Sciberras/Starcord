import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { RouterLink } from '@angular/router';
import { emailValidator, passwordRequiredValidator } from '@app/shared/validators';

@Component({
  selector: 'app-signup',
  imports: [RouterLink, FormsModule, ReactiveFormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent {
  signupForm = new FormGroup({
    email: new FormControl<string>('', [Validators.required, emailValidator()]),
    displayName: new FormControl<string>(''),
    username: new FormControl<string>('', [Validators.required]),
    password: new FormControl<string>('', [Validators.required, passwordRequiredValidator()]),
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

  // TODO: Finish this on submit method
  async onSubmit(): Promise<void> {
    this.signupForm.markAllAsTouched();
  }
}
