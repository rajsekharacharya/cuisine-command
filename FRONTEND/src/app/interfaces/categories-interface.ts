export interface CategoryRequestDTO {
  name: string;
  description?: string;  // Optional, as it can be null or undefined
  status?: boolean;  // Optional, defaults to `true`
}

export interface CategoryResponseDTO {
  id: number;
  name: string;
  description: string;
  image: string;
  status: boolean;
}
