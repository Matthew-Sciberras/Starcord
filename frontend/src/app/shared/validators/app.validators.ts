import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function matchFieldsValidator(
  field: string,
  confirmField: string
): ValidatorFn {

  return (group: AbstractControl): ValidationErrors | null => {
    const control = group.get(field);
    const confirm = group.get(confirmField);

    if (!control || !confirm) return null;

    if (control.value === confirm.value) {
      confirm.setErrors(null);
      return null;
    }

    confirm.setErrors({ fieldsMismatch: true });
    return { fieldsMismatch: true };
  };
}

export function minLengthValidator(min: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;

    return control.value.length >= min
      ? null
      : { minLength: { requiredLength: min } };
  };
}