export interface CartDTO {
  id: string;
  userId: string;
  items: CartItemDTO[];
  subtotal: number;
  tax: number;
  shippingCost: number;
  total: number;
  couponCode: string | null;
  discount: number | null;
}

export interface CartItemDTO {
  productId: string;
  productName: string;
  sku: string;
  quantity: number;
  unitPrice: number;
  subtotal: number;
  imageUrl: string | null;
}
