export interface OrderDTO {
  id: number;
  orderDate?: string;
  orderTime?: string;
  customer?: string;
  status?: OrderStatus;
  orderType?: OrderType;
  paymentMethod: PaymentMethod;
  subtotal?: number;
  totalQuantity?: number;
  discountPercent?: number;
  discountAmount?: number;
  deliveryCharge?: number;
  containerCharge?: number;
  taxPercent?: number;
  taxAmount?: number;
  roundOff?: number;
  finalTotal?: number;
  tableNumber?: string;
}
export interface OrderRequestDTO {
  customerId?: number;
  status?: OrderStatus;
  paymentMethod: PaymentMethod;
  subtotal?: number;
  totalQuantity?: number;
  discountPercent?: number;
  discountAmount?: number;
  deliveryCharge?: number;
  containerCharge?: number;
  taxPercent?: number;
  taxAmount?: number;
  roundOff?: number;
  finalTotal?: number;
  tableNumber?: string;
  orderType?: OrderType;
  orderItems: OrderItemDTO[];
}

export interface OrderItemDTO {
  cartItemId?: number;
  itemId: number;
  variationId?: number;
  dietary: Dietary;
  quantity: number;
  subtotal?: number;
  addons: OrderItemAddonDTO[];
}

export interface OrderItemAddonDTO {
  id?: number;
  name: string;
  price: number;
  variationId?: number;
}

export interface OrderResponseDTO {
  id: number;
  orderDate: string; // Maps to Java LocalDate (serialized as "YYYY-MM-DD")
  orderTime: string; // Maps to Java LocalTime (serialized as "HH:mm:ss")
  customer: CustomerDTO | null;
  status: OrderStatus;
  orderType: OrderType;
  subtotal: number;
  totalQuantity: number;
  discountPercent: number;
  discountAmount: number;
  deliveryCharge: number;
  containerCharge: number;
  taxPercent: number;
  taxAmount: number;
  roundOff: number;
  finalTotal: number;
  tableNumber: string;
  paymentMethod: PaymentMethod;
  orderItems: OrderItemResponseDTO[];
  bill: BillDTO;
  kot: KotDTO;
}

export interface OrderItemResponseDTO {
  id: number;
  itemId: number;
  itemName: string;
  variationId: number | null;
  variationName: string | null;
  price: number;
  quantity: number;
  subtotal: number;
  addons: OrderItemAddonDTO[];
}

export interface CustomerDTO {
  id?: number;
  name: string;
  phone: string;
  email: string;
}

export interface BillDTO {
  orderId: number;
  customerName: string;
  items: BillItemDTO[];
  subtotal: number;
  totalQuantity: number;
  discountPercent: number;
  discountAmount: number;
  deliveryCharge: number;
  containerCharge: number;
  taxPercent: number;
  taxAmount: number;
  roundOff: number;
  finalTotal: number;
  paymentMethod: PaymentMethod;
  tableNumber: string;
}

export interface BillItemDTO {
  itemName: string;
  variationName: string;
  quantity: number;
  price: number;
  subtotal: number;
  addons: OrderItemAddonDTO[];
}

export interface KotDTO {
  orderId: number;
  items: KotItemDTO[];
  tableNumber: string;
  orderType: OrderType;
}

export interface KotItemDTO {
  itemName: string;
  variationName: string;
  quantity: number;
  addons: OrderItemAddonDTO[];
}

export interface PosResponseDTO {
  categories: CategoryDTO[];
}

export interface CategoryDTO {
  id: number;
  name: string;
  image?: string;
  items: ItemDTO[];
}

export interface ItemDTO {
  id: number;
  name: string;
  price: number;
  variation: boolean;
  dietary: Dietary;
  addons: boolean;
  combo: boolean;
  variations: VariationDTO[];
  itemAddons: ItemAddonDTO[];
  selectedAddons: { [key: number]: boolean }; // Added for add-on selection
}

export interface VariationDTO {
  id: number;
  name: string;
  price: number;
  addons: boolean;
  itemAddons: ItemAddonDTO[];
  selectedAddons: { [key: number]: boolean }; // Added for add-on selection
}

export interface ItemAddonDTO {
  id: number;
  name: string;
  price: number;
}

export enum OrderStatus {
  HELD = 'HELD',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED',
}

export enum PaymentMethod {
  CASH = 'CASH',
  CARD = 'CARD',
  ONLINE = 'UPI',
}

export enum Dietary {
  VEG = 'VEG',
  NON_VEG = 'NON_VEG',
  VEGAN = 'VEGAN',
}

export enum OrderType {
  DINE_IN = 'DINE_IN',
  DELIVERY = 'DELIVERY',
  PICKUP = 'PICKUP',
}
