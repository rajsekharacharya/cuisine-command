import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '../../interfaces/login-interface';
import { AuthService } from '../../service/auth/auth-service';
import { ToastService } from '../../service/common/toast-service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login implements OnInit {
  credentials: AuthenticationRequest = { username: '', password: '' };
  error: string | null = null;
  returnUrl: string | null = null;
  buttonState = 'normal';
  hidePassword = true;

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly toast: ToastService
  ) { }

  ngOnInit() {
    if (this.authService.isLoggedIn()) {
      console.log(this.authService.hasRole('SERVER'));
      if (this.authService.hasRole('SERVER')) {
        this.router.navigate(['/sale/pos']);
      } else {
        this.router.navigate(['/dashboard']);
      }
    }
  }


  onSubmit() {
    this.error = null;
    this.authService.login(this.credentials).subscribe({
      next: () => {
        console.log('Login success');
        // Check user role after login
        if (this.authService.hasRole('SERVER')) {
          this.router.navigate(['/sale/pos']);
        } else {
          this.router.navigate(['/dashboard']);
        }
        this.toast.showSuccess('Login Successful!', 3000);
      },
      error: (err) => {
        console.error('Login error:', err);
        this.error =
          err.message || 'Login failed. Please check your credentials.';
        this.toast.showError(this.error || 'Error');
      },
    });
  }



  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  onButtonHover(hovered: boolean) {
    this.buttonState = hovered ? 'hovered' : 'normal';
  }
}
