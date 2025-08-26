import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse, PagedModel } from '../../interfaces/api-response';
import { UsersRequestDTO, UsersResponseDTO } from '../../interfaces/user-interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private readonly http: HttpClient) { }

  createUser(dto: UsersRequestDTO): Observable<ApiResponse<UsersResponseDTO>> {
    return this.http
      .post<ApiResponse<UsersResponseDTO>>(ApiEndpoint.Users.create, dto)
      .pipe(catchError(this.handleError));
  }

  updateUser(userId: number, dto: UsersRequestDTO): Observable<ApiResponse<UsersResponseDTO>> {
    return this.http
      .put<ApiResponse<UsersResponseDTO>>(ApiEndpoint.Users.update(userId), dto)
      .pipe(catchError(this.handleError));
  }

  // Simplified getAllUsers method that accepts only params
  getAllUsers(params?: any): Observable<ApiResponse<PagedModel<UsersResponseDTO>>> {
    return this.http
      .get<ApiResponse<PagedModel<UsersResponseDTO>>>(ApiEndpoint.Users.getAll, { params })
      .pipe(catchError(this.handleError));
  }

  getUserById(userId: number): Observable<ApiResponse<UsersResponseDTO>> {
    return this.http
      .get<ApiResponse<UsersResponseDTO>>(ApiEndpoint.Users.getById(userId))
      .pipe(catchError(this.handleError));
  }

  toggleUserStatus(userId: number): Observable<ApiResponse<string>> {
    return this.http
      .patch<ApiResponse<string>>(ApiEndpoint.Users.toggleStatus(userId), {})
      .pipe(catchError(this.handleError));
  }

  verifyEmail(token: string): Observable<ApiResponse<string>> {
    const params = new HttpParams().set('token', token);
    return this.http
      .get<ApiResponse<string>>(ApiEndpoint.Users.verifyEmail, { params })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client Error: ${error.error.message}`;
    } else {
      errorMessage =
        error.error?.error ?? error.error?.message ?? `Server Error: ${error.status}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
