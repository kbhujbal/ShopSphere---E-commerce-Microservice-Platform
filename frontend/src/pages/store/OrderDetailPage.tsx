import { useParams, Link } from 'react-router-dom';
import { useGetOrderByIdQuery } from '../../features/orders/ordersApi';
import { useGetShipmentByOrderIdQuery } from '../../features/shipments/shipmentsApi';
import { formatCurrency } from '../../utils/formatCurrency';
import { formatDate } from '../../utils/formatDate';
import Badge from '../../components/ui/Badge';
import Spinner from '../../components/ui/Spinner';
import Card from '../../components/ui/Card';

const statusVariant = (status: string | null) => {
  switch (status?.toUpperCase()) {
    case 'PENDING': return 'warning' as const;
    case 'PROCESSING': return 'info' as const;
    case 'SHIPPED': return 'info' as const;
    case 'DELIVERED': return 'success' as const;
    case 'CANCELLED': return 'danger' as const;
    default: return 'default' as const;
  }
};

export default function OrderDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { data: order, isLoading } = useGetOrderByIdQuery(id!);
  const { data: shipment } = useGetShipmentByOrderIdQuery(id!, { skip: !order });

  if (isLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;
  if (!order) return <div className="py-20 text-center text-gray-500">Order not found</div>;

  return (
    <div className="mx-auto max-w-4xl px-4 py-8 sm:px-6">
      <div className="flex items-center justify-between mb-8">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Order #{order.orderNumber}</h1>
          <p className="text-sm text-gray-500">{formatDate(order.orderDate)}</p>
        </div>
        <Badge variant={statusVariant(order.orderStatus)}>
          {order.orderStatus || 'PENDING'}
        </Badge>
      </div>

      <div className="grid gap-6 lg:grid-cols-3">
        <div className="lg:col-span-2 space-y-6">
          <Card className="p-6">
            <h2 className="font-semibold text-gray-900 mb-4">Items</h2>
            <div className="space-y-3">
              {order.items.map((item) => (
                <div key={item.productId} className="flex items-center justify-between">
                  <div>
                    <Link to={`/products/${item.productId}`} className="text-sm font-medium text-gray-900 hover:text-indigo-600">
                      {item.productName}
                    </Link>
                    <p className="text-xs text-gray-500">Qty: {item.quantity} x {formatCurrency(item.unitPrice)}</p>
                  </div>
                  <p className="text-sm font-medium">{formatCurrency(item.subtotal)}</p>
                </div>
              ))}
            </div>
          </Card>

          {shipment && (
            <Card className="p-6">
              <h2 className="font-semibold text-gray-900 mb-4">Shipping</h2>
              <div className="grid grid-cols-2 gap-4 text-sm">
                <div><p className="text-gray-500">Carrier</p><p className="font-medium">{shipment.carrier}</p></div>
                <div><p className="text-gray-500">Tracking</p><p className="font-medium">{shipment.trackingNumber || 'Pending'}</p></div>
                <div><p className="text-gray-500">Status</p><Badge variant={statusVariant(shipment.status)}>{shipment.status || 'PENDING'}</Badge></div>
                <div><p className="text-gray-500">Est. Delivery</p><p className="font-medium">{formatDate(shipment.estimatedDeliveryDate)}</p></div>
              </div>
            </Card>
          )}
        </div>

        <div className="space-y-6">
          <Card className="p-6">
            <h2 className="font-semibold text-gray-900 mb-4">Summary</h2>
            <div className="space-y-2 text-sm">
              <div className="flex justify-between"><span className="text-gray-500">Subtotal</span><span>{formatCurrency(order.subtotal)}</span></div>
              <div className="flex justify-between"><span className="text-gray-500">Tax</span><span>{formatCurrency(order.tax)}</span></div>
              <div className="flex justify-between"><span className="text-gray-500">Shipping</span><span>{formatCurrency(order.shippingCost)}</span></div>
              <hr />
              <div className="flex justify-between font-semibold"><span>Total</span><span>{formatCurrency(order.total)}</span></div>
            </div>
          </Card>

          <Card className="p-6">
            <h2 className="font-semibold text-gray-900 mb-3">Addresses</h2>
            <div className="text-sm space-y-3">
              <div><p className="text-gray-500">Shipping</p><p className="text-gray-700">{order.shippingAddress}</p></div>
              <div><p className="text-gray-500">Billing</p><p className="text-gray-700">{order.billingAddress}</p></div>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
}
