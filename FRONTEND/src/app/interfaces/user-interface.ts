export interface UsersRequestDTO {
  name: string;
  email: string;
  username: string;
  mobile: string;
  password?: string; // Optional for updates
  role: RoleType;
}

export interface UsersResponseDTO {
  id: number;
  name: string;
  email: string;
  username: string;
  mobile: string;
  role: RoleType;
  enabled: boolean;
  verified: boolean;
}

export enum RoleType {
  ADMIN = 'ADMIN',
  MANAGER = 'MANAGER',
  CHEF = 'CHEF',
  SERVER = 'SERVER',
  CUSTOMER = 'CUSTOMER'

}
