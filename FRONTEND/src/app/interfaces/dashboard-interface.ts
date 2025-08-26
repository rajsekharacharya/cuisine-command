export interface DashboardData {
  revenue: PeriodValues;
  completedOrders: PeriodCounts;
  canceledOrders: PeriodCounts;
  topSellingDishes: TopDish[];
  recentOrders: RecentOrder[];
  orderTypeBreakdown: OrderTypeBreakdown;
  averageOrderValue: PeriodValues;
  customerRetention: CustomerRetention;
  peakHours: PeakHours;
  paymentMethodDistribution: PaymentMethodDistribution;
  discountImpact: DiscountImpact;
}

export interface PeriodValues {
  today: number;
  week: number;
  month: number;
  year: number;
}

export interface PeriodCounts {
  today: number;
  week: number;
  month: number;
  year: number;
}

export interface TopDish {
  name: string;
  quantitySold: number;
}

export interface RecentOrder {
  id: number;
  orderDate: string;
  orderTime: string;
  finalTotal: number;
  customerName: string;
  orderType: string;
}

export interface OrderTypeBreakdown {
  PICKUP: number;
  DELIVERY: number;
  DINE_IN: number;
}

export interface CustomerRetention {
  newCustomers: number;
  repeatCustomers: number;
}

export interface PeakHours {
  [hour: string]: number; // e.g., "0": 0, "1": 0, ..., "23": 0
}

export interface PaymentMethodDistribution {
  CASH: number;
  CARD: number;
  UPI: number;
}

export interface DiscountImpact {
  totalDiscount: number;
  discountPercentage: number;
}
