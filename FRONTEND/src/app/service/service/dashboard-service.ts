import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse } from '../../interfaces/api-response';
import { DashboardData } from '../../interfaces/dashboard-interface';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  constructor(private readonly http: HttpClient) {}

  getDashboardData(params?: {
    startDate?: string;
    endDate?: string;
  }): Observable<ApiResponse<DashboardData>> {
    let httpParams = new HttpParams();
    if (params?.startDate) {
      httpParams = httpParams.set('startDate', params.startDate);
    }
    if (params?.endDate) {
      httpParams = httpParams.set('endDate', params.endDate);
    }

    return this.http
      .get<ApiResponse<DashboardData>>(ApiEndpoint.Dashboard.getDashboardData, {
        params: httpParams,
      })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client Error: ${error.error.message}`;
    } else {
      errorMessage =
        error.error?.error ??
        error.error?.message ??
        `Server Error: ${error.status}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
