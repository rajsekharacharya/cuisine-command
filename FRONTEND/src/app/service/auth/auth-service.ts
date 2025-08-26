import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse } from '../../interfaces/api-response';
import { AuthenticationRequest, AuthenticationResponse } from '../../interfaces/login-interface';
import { TokenService } from './token-service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private readonly http: HttpClient,
    private readonly tokenService: TokenService,
    private readonly router: Router
  ) {}

  login(
    payload: AuthenticationRequest
  ): Observable<ApiResponse<AuthenticationResponse>> {
    return this.http
      .post<ApiResponse<AuthenticationResponse>>(
        ApiEndpoint.Auth.login,
        payload
      )
      .pipe(
        tap((response) => {
          if (response.status && response.data) {
            this.tokenService.setTokens({
              accessToken: response.data.accessToken,
              refreshToken: response.data.refreshToken,
            });
            this.sessionTimeout();
          }
        }),
        catchError(this.handleError)
      );
  }

  refreshToken(): Observable<ApiResponse<AuthenticationResponse>> {
    const refreshToken = this.tokenService.refreshToken;
    if (!refreshToken) {
      return throwError(() => new Error('No refresh token available'));
    }

    const params = new HttpParams().set('token', encodeURIComponent(refreshToken));

    return this.http
      .post<ApiResponse<AuthenticationResponse>>(
        ApiEndpoint.Auth.refresh,
        {},
        { params }
      )
      .pipe(
        tap((response) => {
          if (response.status && response.data) {
            this.tokenService.setTokens({
              accessToken: response.data.accessToken,
              refreshToken: response.data.refreshToken,
            });
            this.sessionTimeout();
          }
        }),
        catchError(this.handleError)
      );
  }

  logout(): void {
    this.tokenService.clearTokens();
    this.router.navigate(['/login']);
  }

  sessionTimeout(): void {
    const time = this.tokenService.getTokenExpiration();
    if (!time) {
      console.warn('Token expiration time not available');
      this.tryRefreshOrLogout();
      return;
    }

    const targetTime = new Date(time);
    const now = new Date();
    const timeout = targetTime.getTime() - now.getTime();

    if (timeout > 0) {
      setTimeout(() => {
        this.tryRefreshOrLogout();
      }, timeout);
    } else {
      this.tryRefreshOrLogout();
    }
  }

  private tryRefreshOrLogout(): void {
    if (this.tokenService.isRefreshTokenValid()) {
      this.refreshToken().subscribe({
        next: () => {
          // Refresh successful, sessionTimeout already called in refreshToken
        },
        error: () => {
          this.logout();
        },
      });
    } else {
      this.logout();
    }
  }

  isLoggedIn(): boolean {
    return this.tokenService.isAccessTokenValid();
  }

  get userRoles(): string[] {
    return this.tokenService.getUserRoles();
  }

  hasRole(role: string): boolean {
    return this.userRoles.includes(role);
  }

  hasAnyRole(roles: string[]): boolean {
    return roles.some((role) => this.userRoles.includes(role));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client Error: ${error.error.message}`;
    } else {
      errorMessage =
        error.error?.error ??
        error.error?.message ??
        `Server Error: ${error.status}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
