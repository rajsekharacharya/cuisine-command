import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse, PagedModel } from '../../interfaces/api-response';
import { AddonsRequestDTO, AddonsResponseDTO } from '../../interfaces/addons-interface';

@Injectable({
  providedIn: 'root',
})
export class AddonsService {
  constructor(private readonly http: HttpClient) {}

  createAddons(dto: AddonsRequestDTO): Observable<ApiResponse<AddonsResponseDTO>> {
    return this.http
      .post<ApiResponse<AddonsResponseDTO>>(ApiEndpoint.Addons.create, dto)
      .pipe(catchError(this.handleError));
  }

  updateAddons(addonsId: number, dto: AddonsRequestDTO): Observable<ApiResponse<AddonsResponseDTO>> {
    return this.http
      .put<ApiResponse<AddonsResponseDTO>>(ApiEndpoint.Addons.update(addonsId), dto)
      .pipe(catchError(this.handleError));
  }

  getAllAddons(params?: any): Observable<ApiResponse<PagedModel<AddonsResponseDTO>>> {
    return this.http
      .get<ApiResponse<PagedModel<AddonsResponseDTO>>>(ApiEndpoint.Addons.getAll, { params })
      .pipe(catchError(this.handleError));
  }

  getAddonsById(addonsId: number): Observable<ApiResponse<AddonsResponseDTO>> {
    return this.http
      .get<ApiResponse<AddonsResponseDTO>>(ApiEndpoint.Addons.getById(addonsId))
      .pipe(catchError(this.handleError));
  }

  toggleAddonsStatus(addonsId: number): Observable<ApiResponse<string>> {
    return this.http
      .patch<ApiResponse<string>>(ApiEndpoint.Addons.toggleStatus(addonsId), {})
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
