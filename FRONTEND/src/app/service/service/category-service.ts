import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse, PagedModel } from '../../interfaces/api-response';
import { CategoryRequestDTO, CategoryResponseDTO } from '../../interfaces/categories-interface';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private readonly http: HttpClient) {}

  createCategory(dto: CategoryRequestDTO, image?: File): Observable<ApiResponse<CategoryResponseDTO>> {
    const formData = new FormData();
    formData.append('data', new Blob([JSON.stringify(dto)], { type: 'application/json' }));
    if (image) formData.append('image', image);

    return this.http
      .post<ApiResponse<CategoryResponseDTO>>(ApiEndpoint.Categories.create, formData)
      .pipe(catchError(this.handleError));
  }

  updateCategory(categoryId: number, dto: CategoryRequestDTO, image?: File): Observable<ApiResponse<CategoryResponseDTO>> {
    const formData = new FormData();
    formData.append('data', new Blob([JSON.stringify(dto)], { type: 'application/json' }));
    if (image) formData.append('image', image);

    return this.http
      .put<ApiResponse<CategoryResponseDTO>>(ApiEndpoint.Categories.update(categoryId), formData)
      .pipe(catchError(this.handleError));
  }

  getAllCategories(params?: any): Observable<ApiResponse<PagedModel<CategoryResponseDTO>>> {
    return this.http
      .get<ApiResponse<PagedModel<CategoryResponseDTO>>>(ApiEndpoint.Categories.getAll, { params })
      .pipe(catchError(this.handleError));
  }

  getCategoryById(categoryId: number): Observable<ApiResponse<CategoryResponseDTO>> {
    return this.http
      .get<ApiResponse<CategoryResponseDTO>>(ApiEndpoint.Categories.getById(categoryId))
      .pipe(catchError(this.handleError));
  }

  toggleCategoryStatus(categoryId: number): Observable<ApiResponse<string>> {
    return this.http
      .patch<ApiResponse<string>>(ApiEndpoint.Categories.toggleStatus(categoryId), {})
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
