import { animate, state, style, transition, trigger } from '@angular/animations';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../service/service/user-service';

@Component({
  selector: 'app-email-verifier',
  standalone: true,
  imports: [CommonModule, MatSnackBarModule, MatProgressSpinnerModule],
  templateUrl: './email-verifier.html',
  styleUrl: './email-verifier.scss',
  animations: [
    trigger('fadeInOut', [
      state('in', style({ opacity: 1, transform: 'translateY(0)' })),
      transition('void => in', [
        style({ opacity: 0, transform: 'translateY(20px)' }),
        animate('500ms ease-out')
      ]),
      transition('in => void', [
        animate('500ms ease-in', style({ opacity: 0, transform: 'translateY(-20px)' }))
      ])
    ]),
    trigger('spinnerRotate', [
      state('rotate', style({ transform: 'rotate(360deg)' })),
      transition('* => rotate', [
        animate('1s linear')
      ])
    ])
  ]
})
export class EmailVerifier implements OnInit {
  isVerifying = true;
  errorMessage: string | null = null;
  animationState = 'in';
  spinnerState = 'rotate';

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly userService: UserService,
    private readonly snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    const token = this.route.snapshot.queryParams['token'];
    if (!token) {
      this.errorMessage = 'Invalid verification link. No token provided.';
      this.isVerifying = false;
      return;
    }

    this.userService.verifyEmail(token).subscribe({
      next: (response) => {
        if (response.status && response.data) {
          this.snackBar.open('Email verified successfully!', 'Close', {
            duration: 3000,
            verticalPosition: 'top',
            panelClass: ['success-snackbar']
          });
          this.isVerifying = false; // Show success message
          setTimeout(() => {
            this.animationState = 'out';
            setTimeout(() => {
              this.router.navigate(['/login']);
            }, 500); // Match animation duration
          }, 3000); // Wait for snackbar
        } else {
          this.errorMessage = response.message || 'Email verification failed.';
          this.isVerifying = false;
        }
      },
      error: (err) => {
        this.errorMessage = err.message || 'An error occurred during verification.';
        this.isVerifying = false;
      }
    });
  }

  navigateToLogin() {
    this.animationState = 'out';
    setTimeout(() => {
      this.router.navigate(['/login']);
    }, 500); // Match animation duration
  }
}
