import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ToastComponent } from '@shared/components/toast/toast.component'; // adjust path if needed

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackBar: MatSnackBar) {}

  showError(message: string): void {
    this.snackBar.openFromComponent(ToastComponent, {
      data: { message, type: 'error' },
      duration: 2500,
      verticalPosition: 'top',
      horizontalPosition: 'center',
      panelClass:['snackbar-error']
    });
  }

  showSuccess(message: string): void {
    this.snackBar.openFromComponent(ToastComponent, {
      data: { message, type: 'success' },
      duration: 2500,
      verticalPosition: 'top',
      horizontalPosition: 'center',
      panelClass:['snackbar-success']
    });
  }

  showInfo(message: string): void {
    this.snackBar.openFromComponent(ToastComponent, {
      data: { message, type: 'info' },
      duration: 2500,
      verticalPosition: 'top',
      horizontalPosition: 'center',
      panelClass:['snackbar-info']
    });
  }
}
