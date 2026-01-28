import { useParams } from 'react-router-dom';
import { useGetOrderByIdQuery, useUpdateOrderStatusMutation } from '../../features/orders/ordersApi';
import { formatCurrency } from '../../utils/formatCurrency';
import { formatDate } from '../../utils/formatDate';
import Badge from '../../components/ui/Badge';
import Card from '../../components/ui/Card';
import Select from '../../components/ui/Select';
import Spinner from '../../components/ui/Spinner';

const statuses = ['PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED'];
const statusVariant = (status: string) => {
  switch (status) {
    case 'PENDING': return 'warning' as const;
    case 'PROCESSING': return 'info' as const;
    case 'SHIPPED': return 'info' as const;
    case 'DELIVERED': return 'success' as const;
    case 'CANCELLED': return 'danger' as const;
    default: return 'default' as const;
  }
};

export default function AdminOrderDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { data: order, isLoading } = useGetOrderByIdQuery(id!);
  const [updateStatus] = useUpdateOrderStatusMutation();

  if (isLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;
  if (!order) return <div className="py-20 text-center text-gray-500">Order not found</div>;

  return (
    <div>
      <div className="flex items-center justify-between mb-8">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Order #{order.orderNumber}</h1>
          <p className="text-sm text-gray-500">{formatDate(order.orderDate)}</p>
        </div>
        <div className="flex items-center gap-3">
          <Badge variant={statusVariant(order.orderStatus || 'PENDING')}>{order.orderStatus || 'PENDING'}</Badge>
          <Select
            options={statuses.map((s) => ({ value: s, label: s }))}
            value={order.orderStatus || 'PENDING'}
            onChange={(e) => updateStatus({ id: order.id, orderStatus: e.target.value })}
            className="w-40"
          />
        </div>
      </div>

      <div className="grid gap-6 lg:grid-cols-3">
        <div className="lg:col-span-2">
          <Card className="p-6">
            <h2 className="font-semibold mb-4">Items</h2>
            <div className="space-y-3">
              {order.items.map((item) => (
                <div key={item.productId} className="flex justify-between text-sm">
                  <div>
                    <p className="font-medium">{item.productName}</p>
                    <p className="text-gray-500">SKU: {item.sku} &middot; Qty: {item.quantity}</p>
                  </div>
                  <p className="font-medium">{formatCurrency(item.subtotal)}</p>
                </div>
              ))}
            </div>
          </Card>
        </div>
        <div>
          <Card className="p-6">
            <h2 className="font-semibold mb-4">Summary</h2>
            <div className="space-y-2 text-sm">
              <div className="flex justify-between"><span className="text-gray-500">Subtotal</span><span>{formatCurrency(order.subtotal)}</span></div>
              <div className="flex justify-between"><span className="text-gray-500">Tax</span><span>{formatCurrency(order.tax)}</span></div>
              <div className="flex justify-between"><span className="text-gray-500">Shipping</span><span>{formatCurrency(order.shippingCost)}</span></div>
              <hr />
              <div className="flex justify-between font-semibold"><span>Total</span><span>{formatCurrency(order.total)}</span></div>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
}
