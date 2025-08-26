import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse, PagedModel } from '../../interfaces/api-response';
import { RestaurantRequestDTO, RestaurantResponseDTO } from '../../interfaces/restaurants-interface';

@Injectable({
  providedIn: 'root',
})
export class RestaurantService {
  constructor(private readonly http: HttpClient) { }

  createRestaurant(dto: RestaurantRequestDTO, logo?: File, banner?: File): Observable<ApiResponse<RestaurantResponseDTO>> {
    const formData = new FormData();
    formData.append('data', new Blob([JSON.stringify(dto)], { type: 'application/json' }));
    if (logo) formData.append('logo', logo);
    if (banner) formData.append('banner', banner);

    return this.http
      .post<ApiResponse<RestaurantResponseDTO>>(ApiEndpoint.Restaurants.create, formData)
      .pipe(catchError(this.handleError));
  }

  updateRestaurant(restaurantId: number, dto: RestaurantRequestDTO, logo?: File, banner?: File): Observable<ApiResponse<RestaurantResponseDTO>> {
    const formData = new FormData();
    formData.append('data', new Blob([JSON.stringify(dto)], { type: 'application/json' }));
    if (logo) formData.append('logo', logo);
    if (banner) formData.append('banner', banner);

    return this.http
      .put<ApiResponse<RestaurantResponseDTO>>(ApiEndpoint.Restaurants.update(restaurantId), formData)
      .pipe(catchError(this.handleError));
  }

  getAllRestaurants(params?: any): Observable<ApiResponse<PagedModel<RestaurantResponseDTO>>> {
    return this.http
      .get<ApiResponse<PagedModel<RestaurantResponseDTO>>>(ApiEndpoint.Restaurants.getAll, { params })
      .pipe(catchError(this.handleError));
  }

  getRestaurantById(restaurantId: number): Observable<ApiResponse<RestaurantResponseDTO>> {
    return this.http
      .get<ApiResponse<RestaurantResponseDTO>>(ApiEndpoint.Restaurants.getById(restaurantId))
      .pipe(catchError(this.handleError));
  }

  toggleRestaurantStatus(restaurantId: number): Observable<ApiResponse<string>> {
    return this.http
      .patch<ApiResponse<string>>(ApiEndpoint.Restaurants.toggleStatus(restaurantId), {})
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
