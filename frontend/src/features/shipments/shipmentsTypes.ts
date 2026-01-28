export interface ShipmentDTO {
  id: string;
  orderId: string;
  userId: string;
  trackingNumber: string | null;
  carrier: string;
  serviceType: string;
  weight: number;
  weightUnit: string;
  shippingCost: number | null;
  status: string | null;
  estimatedDeliveryDate: string | null;
  actualDeliveryDate: string | null;
  destinationAddress: string;
  destinationCity: string;
  destinationState: string;
  destinationCountry: string;
  destinationPostalCode: string;
  notes: string | null;
}
