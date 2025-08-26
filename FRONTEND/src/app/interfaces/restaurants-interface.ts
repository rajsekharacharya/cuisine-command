export interface RestaurantRequestDTO {
  name: string;
  cuisine?: string;  // Optional
  description?: string;  // Optional
  address?: string;  // Optional
  contact?: string;  // Optional
  status?: boolean;  // Optional, defaults to true
}

export interface RestaurantResponseDTO {
  id: number;
  name: string;
  cuisine: string;
  description: string;
  address: string;
  contact: string;
  logo: string;
  banner: string;
  status: boolean;
}
