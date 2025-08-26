export interface AddonsRequestDTO {
  name: string;
  description?: string;  // Optional, since it can be null or undefined
  status?: boolean;  // Optional, defaults to `true`
}

export interface AddonsResponseDTO {
  id: number;
  name: string;
  description: string;
  status: boolean;
}
