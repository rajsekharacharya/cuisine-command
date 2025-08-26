import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from './token-service';
import { AuthService } from './auth-service';
import { catchError, map, of, switchMap } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService);
  const authService = inject(AuthService);
  const router = inject(Router);

  if (tokenService.isAccessTokenValid()) {
    const requiredRoles = route.data?.['roles'] as string[] | undefined;
    if (requiredRoles) {
      const userRoles = tokenService.getUserRoles();
      const hasRequiredRole = requiredRoles.some((role) => userRoles.includes(role));
      console.log('User roles:', userRoles);
      console.log('Required roles:', requiredRoles);
      if (!hasRequiredRole) {
        router.navigate(['/forbidden'], { queryParams: { returnUrl: state.url } });
        return false;
      }
    }
    return true;
  }

  if (tokenService.needsRefresh() && tokenService.isRefreshTokenValid()) {
    return authService.refreshToken().pipe(
      switchMap((response) => {
        if (response.status && response.data) {
          const requiredRoles = route.data?.['roles'] as string[] | undefined;
          if (requiredRoles) {
            const userRoles = tokenService.getUserRoles();
            const hasRequiredRole = requiredRoles.some((role) => userRoles.includes(role));
            if (!hasRequiredRole) {
              router.navigate(['/forbidden'], { queryParams: { returnUrl: state.url } });
              return of(false);
            }
          }
          return of(true);
        }
        router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
        return of(false);
      }),
      catchError(() => {
        router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
        return of(false);
      })
    );
  }

  router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
  return false;
};
