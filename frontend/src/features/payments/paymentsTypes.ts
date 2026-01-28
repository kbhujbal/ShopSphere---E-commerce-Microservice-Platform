export interface PaymentDTO {
  id: string;
  orderId: string;
  userId: string;
  amount: number;
  currency: string;
  paymentMethod: string;
  paymentMethodId: string | null;
  paymentIntentId: string | null;
  status: string | null;
  description: string | null;
  receiptUrl: string | null;
  paymentDate: string | null;
  errorMessage: string | null;
  errorCode: string | null;
  customerId: string | null;
  customerEmail: string | null;
  customerName: string | null;
  billingAddress: string;
  billingCity: string;
  billingState: string;
  billingCountry: string;
  billingPostalCode: string;
  shippingAddress: string;
  shippingCity: string;
  shippingState: string;
  shippingCountry: string;
  shippingPostalCode: string;
}
