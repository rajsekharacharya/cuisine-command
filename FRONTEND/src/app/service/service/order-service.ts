import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiEndpoint } from '../../config/api-config';
import { ApiResponse, PagedModel } from '../../interfaces/api-response';
import {
  CustomerDTO,
  OrderDTO,
  OrderRequestDTO,
  OrderResponseDTO,
} from '../../interfaces/orders-interface';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(private readonly http: HttpClient) {}

  getAllOrder(params?: any): Observable<ApiResponse<PagedModel<OrderDTO>>> {
    return this.http
      .get<ApiResponse<PagedModel<OrderDTO>>>(ApiEndpoint.Orders.getAllOrders, {
        params,
      })
      .pipe(catchError(this.handleError));
  }

  createOrder(dto: OrderRequestDTO): Observable<ApiResponse<OrderResponseDTO>> {
    return this.http
      .post<ApiResponse<OrderResponseDTO>>(ApiEndpoint.Orders.create, dto)
      .pipe(catchError(this.handleError));
  }

  updateOrder(
    orderId: number,
    dto: OrderRequestDTO
  ): Observable<ApiResponse<OrderRequestDTO>> {
    return this.http
      .put<ApiResponse<OrderRequestDTO>>(
        ApiEndpoint.Orders.update(orderId),
        dto
      )
      .pipe(catchError(this.handleError));
  }

  getOrderById(orderId: number): Observable<ApiResponse<OrderResponseDTO>> {
    return this.http
      .get<ApiResponse<OrderResponseDTO>>(ApiEndpoint.Orders.getById(orderId))
      .pipe(catchError(this.handleError));
  }

  finalizeOrder(
    orderId: number,
    dto: OrderRequestDTO
  ): Observable<ApiResponse<OrderResponseDTO>> {
    return this.http
      .patch<ApiResponse<OrderResponseDTO>>(
        ApiEndpoint.Orders.finalize(orderId),
        dto
      )
      .pipe(catchError(this.handleError));
  }

  cancelOrder(orderId: number): Observable<ApiResponse<string>> {
    return this.http
      .patch<ApiResponse<string>>(ApiEndpoint.Orders.cancel(orderId), {})
      .pipe(catchError(this.handleError));
  }

  // getHeldOrders with params to allow pagination, sorting, etc.
  getHeldOrders(params?: any): Observable<ApiResponse<OrderResponseDTO[]>> {
    return this.http
      .get<ApiResponse<OrderResponseDTO[]>>(ApiEndpoint.Orders.getHeldOrders, {
        params,
      })
      .pipe(catchError(this.handleError));
  }

  getCustomers(): Observable<ApiResponse<CustomerDTO[]>> {
    return this.http
      .get<ApiResponse<CustomerDTO[]>>(ApiEndpoint.Customer.getAll)
      .pipe(catchError(this.handleError));
  }

  createCustomer(customer: CustomerDTO): Observable<ApiResponse<CustomerDTO>> {
    return this.http
      .post<ApiResponse<CustomerDTO>>(ApiEndpoint.Customer.create, customer)
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
