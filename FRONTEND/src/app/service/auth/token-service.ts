import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private readonly ACCESS_TOKEN = 'access_token';
  private readonly REFRESH_TOKEN = 'refresh_token';
  private readonly jwtHelper = new JwtHelperService();
  private readonly EXPIRATION_BUFFER_SECONDS = 30;
  private readonly STORAGE = environment.useLocalStorage ? localStorage : sessionStorage;

  constructor() {}

  setTokens(tokens: { accessToken: string; refreshToken: string }): void {
    if (tokens.accessToken) {
      this.STORAGE.setItem(this.ACCESS_TOKEN, tokens.accessToken);
    }
    if (tokens.refreshToken) {
      this.STORAGE.setItem(this.REFRESH_TOKEN, tokens.refreshToken);
    }
  }

  get accessToken(): string | null {
    return this.STORAGE.getItem(this.ACCESS_TOKEN);
  }

  get refreshToken(): string | null {
    return this.STORAGE.getItem(this.REFRESH_TOKEN);
  }

  clearTokens(): void {
    this.STORAGE.removeItem(this.ACCESS_TOKEN);
    this.STORAGE.removeItem(this.REFRESH_TOKEN);
  }

  isAccessTokenValid(): boolean {
    try {
      const token = this.accessToken;
      if (!token || typeof token !== 'string') {
        return false;
      }

      const expiration = this.jwtHelper.getTokenExpirationDate(token);
      if (!expiration) {
        return false;
      }

      const now = new Date();
      const bufferTime = this.EXPIRATION_BUFFER_SECONDS * 1000;
      return expiration.getTime() > now.getTime() + bufferTime;
    } catch (error) {
      console.error('Error validating access token:', error);
      return false;
    }
  }

  isRefreshTokenValid(): boolean {
    try {
      const token = this.refreshToken;
      if (!token || typeof token !== 'string') {
        return false;
      }
      return !this.jwtHelper.isTokenExpired(token);
    } catch (error) {
      console.error('Error validating refresh token:', error);
      return false;
    }
  }

  getTokenExpiration(): Date | null {
    try {
      const token = this.accessToken;
      if (!token || typeof token !== 'string') {
        return null;
      }
      return this.jwtHelper.getTokenExpirationDate(token) || null;
    } catch (error) {
      console.error('Error getting token expiration:', error);
      return null;
    }
  }

  getUserRoles(): string[] {
    try {
      const token = this.accessToken;
      if (!token || typeof token !== 'string') {
        return [];
      }

      const decoded = this.jwtHelper.decodeToken(token);
      const authorities = decoded?.Authorities || decoded?.roles || decoded?.scope;

      if (!authorities) {
        return [];
      }

      if (Array.isArray(authorities)) {
        return authorities;
      }

      if (typeof authorities === 'string') {
        return authorities.split(',').map((role) => role.trim());
      }

      return [];
    } catch (error) {
      console.error('Error decoding user roles:', error);
      return [];
    }
  }

  needsRefresh(): boolean {
    try {
      const token = this.accessToken;
      if (!token || typeof token !== 'string') {
        return true;
      }

      const expiration = this.jwtHelper.getTokenExpirationDate(token);
      if (!expiration) {
        return true;
      }

      const now = new Date();
      const bufferTime = this.EXPIRATION_BUFFER_SECONDS * 1000;
      return expiration.getTime() < now.getTime() + bufferTime;
    } catch (error) {
      console.error('Error checking token refresh:', error);
      return true;
    }
  }
}
