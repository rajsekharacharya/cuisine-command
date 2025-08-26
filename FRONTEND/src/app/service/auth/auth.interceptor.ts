import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenService } from './token-service';
import { AuthService } from './auth-service';
import { catchError, switchMap, throwError, of } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn) => {
  const tokenService = inject(TokenService);
  const authService = inject(AuthService);

  const isPublicEndpoint = req.url.includes('/login') || req.url.includes('/refresh') || req.url.includes('/verify');
  if (isPublicEndpoint) {
    return next(req);
  }

  if (tokenService.isAccessTokenValid()) {
    return handleRequestWithToken(req, next, tokenService.accessToken!);
  }

  if (tokenService.needsRefresh() && tokenService.isRefreshTokenValid()) {
    return authService.refreshToken().pipe(
      switchMap((response) => {
        if (response.status && response.data) {
          return handleRequestWithToken(req, next, response.data.accessToken);
        }
        authService.logout();
        return next(req);
      }),
      catchError(() => {
        authService.logout();
        return next(req);
      })
    );
  }

  authService.logout();
  return next(req);
};

function handleRequestWithToken(req: HttpRequest<any>, next: HttpHandlerFn, token: string) {
  const clonedRequest = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  return next(clonedRequest).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        const authService = inject(AuthService);
        authService.logout();
      }
      return throwError(() => error);
    })
  );
}
