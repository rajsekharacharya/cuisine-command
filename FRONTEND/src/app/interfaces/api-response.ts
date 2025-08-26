export interface ApiResponse<T> {
  status?: boolean;
  message?: string;
  error?: string;
  data?: T;
}

export interface PagedModel<T> {
  content: T[];
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  };
}
