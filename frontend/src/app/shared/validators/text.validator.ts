import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function safeTextValidator(): ValidatorFn {
  const safePattern = /^[a-zA-Z0-9 .,!?@()_-]*$/;

  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;

    return safePattern.test(control.value)
      ? null
      : { unsafeCharacters: true };
  };
}