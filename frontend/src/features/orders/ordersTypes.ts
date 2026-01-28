export interface OrderDTO {
  id: string;
  userId: string;
  orderNumber: string;
  items: OrderItemDTO[];
  subtotal: number;
  tax: number;
  shippingCost: number;
  total: number;
  shippingAddress: string;
  billingAddress: string;
  paymentMethod: string;
  paymentStatus: string | null;
  orderStatus: string | null;
  orderDate: string | null;
  estimatedDeliveryDate: string | null;
  trackingNumber: string | null;
  notes: string | null;
}

export interface OrderItemDTO {
  productId: string;
  productName: string;
  sku: string;
  quantity: number;
  unitPrice: number;
  subtotal: number;
  imageUrl: string | null;
}
