import { inject } from '@angular/core';
import { CanActivateChildFn, Router } from '@angular/router';
import { TokenService } from './token-service';

// Create canActivateChild guard function similar to authGuard:
export const authGuardChild: CanActivateChildFn = (route, state) => {
  // reuse your authGuard logic or write similar role check here
  // Same role check logic:
  const tokenService = inject(TokenService);
  const router = inject(Router);

  if (!tokenService.isAccessTokenValid()) {
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }

  const requiredRoles = route.data?.['roles'] as string[] | undefined;
  if (requiredRoles) {
    const userRoles = tokenService.getUserRoles();
    const hasRequiredRole = requiredRoles.some((role) => userRoles.includes(role));
    if (!hasRequiredRole) {
      router.navigate(['/forbidden'], { queryParams: { returnUrl: state.url } });
      return false;
    }
  }

  return true;
};
