export interface ItemRequestDTO {
  categoryId: number;
  name: string;
  shortCode?: string;
  price?: number;
  description?: string;
  dietary: Dietary;
  gstType?: GstType;
  orderTypes: OrderType[];
  variation: boolean;
  addons: boolean;
  combo: boolean;
  status: boolean;
  variations: VariationDTO[];
  itemAddons: ItemAddonDTO[];
}

export interface ItemResponseDTO {
  id: number;
  categoryId: number;
  categoryName: string;
  name: string;
  shortCode?: string;
  price?: number;
  description?: string;
  dietary: Dietary;
  gstType?: GstType;
  orderTypes: OrderType[];
  variation: boolean;
  addons: boolean;
  combo: boolean;
  status: boolean;
  variations: VariationDTO[];
  itemAddons: ItemAddonDTO[];
}

export interface VariationDTO {
  id?: number;
  name: string;
  price?: number;
  status: boolean;
  addons: boolean;
  itemAddons: ItemAddonDTO[];
}

export interface ItemAddonDTO {
  id?: number;
  name: string;
  price?: number;
  status: boolean;
}


export enum Dietary {
  VEG = 'VEG',
  NON_VEG = 'NON_VEG',
  VEGAN = 'VEGAN',
}

export enum OrderType {
  DINE_IN = 'DINE_IN',
  PICKUP = 'PICKUP',
  DELIVERY = 'DELIVERY',
}

export enum GstType {
  INCLUSIVE = 'INCLUSIVE',
  EXCLUSIVE = 'EXCLUSIVE',
  EXEMPTED = 'EXEMPTED',
  NIL = 'NIL',
  COMPOSITION = 'COMPOSITION'
}
