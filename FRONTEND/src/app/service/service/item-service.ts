import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse, PagedModel } from '../../interfaces/api-response';
import { ItemRequestDTO, ItemResponseDTO } from '../../interfaces/items-interface';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  constructor(private readonly http: HttpClient) {}

  createItem(dto: ItemRequestDTO): Observable<ApiResponse<ItemResponseDTO>> {
    return this.http
      .post<ApiResponse<ItemResponseDTO>>(ApiEndpoint.Items.create, dto)
      .pipe(catchError(this.handleError));
  }

  updateItem(itemId: number, dto: ItemRequestDTO): Observable<ApiResponse<ItemResponseDTO>> {
    return this.http
      .put<ApiResponse<ItemResponseDTO>>(ApiEndpoint.Items.update(itemId), dto)
      .pipe(catchError(this.handleError));
  }

  getAllItems(params?: any): Observable<ApiResponse<PagedModel<ItemResponseDTO>>> {
    return this.http
      .get<ApiResponse<PagedModel<ItemResponseDTO>>>(ApiEndpoint.Items.getAll, { params })
      .pipe(catchError(this.handleError));
  }

  getItemById(itemId: number): Observable<ApiResponse<ItemResponseDTO>> {
    return this.http
      .get<ApiResponse<ItemResponseDTO>>(ApiEndpoint.Items.getById(itemId))
      .pipe(catchError(this.handleError));
  }

  toggleItemStatus(itemId: number): Observable<ApiResponse<string>> {
    return this.http
      .patch<ApiResponse<string>>(ApiEndpoint.Items.toggleStatus(itemId), {})
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
