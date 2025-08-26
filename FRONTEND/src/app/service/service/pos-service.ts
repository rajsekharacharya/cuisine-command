import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse } from '../../interfaces/api-response';
import { PosResponseDTO } from '../../interfaces/orders-interface';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class PosService {
  constructor(private readonly http: HttpClient) {}

  // getPosData now accepts params as an argument
  getPosData(params?: any): Observable<ApiResponse<PosResponseDTO>> {
    return this.http
      .get<ApiResponse<PosResponseDTO>>(ApiEndpoint.Items.getPosData, { params })
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
