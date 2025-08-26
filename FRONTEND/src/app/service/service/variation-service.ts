import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse, PagedModel } from '../../interfaces/api-response';
import { VariationRequestDTO, VariationResponseDTO } from '../../interfaces/variations-interface';

@Injectable({
  providedIn: 'root',
})
export class VariationService {
  constructor(private readonly http: HttpClient) {}

  createVariation(dto: VariationRequestDTO): Observable<ApiResponse<VariationResponseDTO>> {
    return this.http
      .post<ApiResponse<VariationResponseDTO>>(ApiEndpoint.Variations.create, dto)
      .pipe(catchError(this.handleError));
  }

  updateVariation(variationId: number, dto: VariationRequestDTO): Observable<ApiResponse<VariationResponseDTO>> {
    return this.http
      .put<ApiResponse<VariationResponseDTO>>(ApiEndpoint.Variations.update(variationId), dto)
      .pipe(catchError(this.handleError));
  }

  getAllVariations(params?: any): Observable<ApiResponse<PagedModel<VariationResponseDTO>>> {
    return this.http
      .get<ApiResponse<PagedModel<VariationResponseDTO>>>(ApiEndpoint.Variations.getAll, { params })
      .pipe(catchError(this.handleError));
  }

  getVariationById(variationId: number): Observable<ApiResponse<VariationResponseDTO>> {
    return this.http
      .get<ApiResponse<VariationResponseDTO>>(ApiEndpoint.Variations.getById(variationId))
      .pipe(catchError(this.handleError));
  }

  toggleVariationStatus(variationId: number): Observable<ApiResponse<string>> {
    return this.http
      .patch<ApiResponse<string>>(ApiEndpoint.Variations.toggleStatus(variationId), {})
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
