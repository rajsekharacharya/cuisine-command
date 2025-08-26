import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../service/auth/auth-service';

@Component({
  selector: 'app-forbidden',
  standalone: true,
  template: `
    <h1>Access Denied</h1>
    <p>You do not have permission to access this page.</p>
    <a (click)="navigateToLogin()">Go to Login</a>
    <a (click)="logout()">Go to Logout</a>
  `,
  styles: `
    :host {
      display: block;
      text-align: center;
      padding: 20px;
    }
    h1 {
      color: #d32f2f;
    }
    a {
      color: #1976d2;
      cursor: pointer;
      text-decoration: underline;
    }
  `,
})
export class Forbidden {
  private readonly returnUrl: string | null;

  constructor(private readonly authService:AuthService,private readonly route: ActivatedRoute, private readonly router: Router) {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/login';
  }

  navigateToLogin() {
    this.router.navigate(['/login'], {
      queryParams: { returnUrl: this.returnUrl },
    });
  }

    logout() {
    this.authService.logout();
    // this.toast.showSuccess('Logout Successful.');
    this.router.navigate(['/login']);
  }
}
