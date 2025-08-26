export interface VariationRequestDTO {
  name: string;
  description?: string;  // Optional, as it can be null or undefined
  status?: boolean;  // Optional, defaults to `true`
}

export interface VariationResponseDTO {
  id: number;
  name: string;
  description: string;
  status: boolean;
}
