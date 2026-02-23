import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function passwordStrengthValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) return null;

    const errors = {
      hasUpper: /[A-Z]/.test(value),
      hasLower: /[a-z]/.test(value),
      hasNumber: /\d/.test(value),
      minLength: value.length >= 8
    };

    const valid = Object.values(errors).every(Boolean);

    return valid ? null : { passwordStrength: errors };
  };
}

export function passwordRequiredValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    // If empty, return error
    if (!value || value.trim() === '') {
      return { passwordRequired: true };
    }

    // Otherwise valid
    return null;
  };
}