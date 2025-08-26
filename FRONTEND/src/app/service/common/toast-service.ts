import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig, MatSnackBarRef, TextOnlySnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  private readonly defaultConfig: MatSnackBarConfig = {
    duration: 30000000000000000000,
    verticalPosition: 'bottom',
    horizontalPosition: 'center',
    politeness: 'polite', // For accessibility
  };

  constructor(private readonly snackBar: MatSnackBar) {}

  /**
   * Shows a success notification
   * @param message The message to display
   * @param duration Duration in milliseconds (default: 3000)
   * @param action Optional action button label (default: 'Close')
   * @returns MatSnackBarRef for manual control
   */
  showSuccess(message: string, duration: number = 3000, action: string = 'Close'): MatSnackBarRef<TextOnlySnackBar> {
    const config: MatSnackBarConfig = {
      ...this.defaultConfig,
      duration,
      panelClass: ['snackbar-success'],
    };
    return this.snackBar.open(message, action, config);
  }

  /**
   * Shows an error notification
   * @param message The message to display
   * @param duration Duration in milliseconds (default: 3000)
   * @param action Optional action button label (default: 'Close')
   * @returns MatSnackBarRef for manual control
   */
  showError(message: string, duration: number = 3000, action: string = 'Close'): MatSnackBarRef<TextOnlySnackBar> {
    const config: MatSnackBarConfig = {
      ...this.defaultConfig,
      duration,
      panelClass: ['snackbar-error'],
    };
    return this.snackBar.open(message, action, config);
  }

  /**
   * Shows an info notification
   * @param message The message to display
   * @param duration Duration in milliseconds (default: 3000)
   * @param action Optional action button label (default: 'Close')
   * @returns MatSnackBarRef for manual control
   */
  showInfo(message: string, duration: number = 3000, action: string = 'Close'): MatSnackBarRef<TextOnlySnackBar> {
    const config: MatSnackBarConfig = {
      ...this.defaultConfig,
      duration,
      panelClass: ['snackbar-info'],
    };
    return this.snackBar.open(message, action, config);
  }

  /**
   * Shows a warning notification
   * @param message The message to display
   * @param duration Duration in milliseconds (default: 3000)
   * @param action Optional action button label (default: 'Close')
   * @returns MatSnackBarRef for manual control
   */
  showWarning(message: string, duration: number = 3000, action: string = 'Close'): MatSnackBarRef<TextOnlySnackBar> {
    const config: MatSnackBarConfig = {
      ...this.defaultConfig,
      duration,
      panelClass: ['snackbar-warning'],
    };
    return this.snackBar.open(message, action, config);
  }

  /**
   * Dismisses all open snackbars
   */
  dismissAll(): void {
    this.snackBar.dismiss();
  }
}
