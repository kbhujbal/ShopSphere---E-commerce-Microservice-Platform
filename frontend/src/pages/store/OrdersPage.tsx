import { Link } from 'react-router-dom';
import { Package } from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import { useGetOrdersByUserIdQuery } from '../../features/orders/ordersApi';
import { formatCurrency } from '../../utils/formatCurrency';
import { formatDate } from '../../utils/formatDate';
import Badge from '../../components/ui/Badge';
import Spinner from '../../components/ui/Spinner';
import EmptyState from '../../components/ui/EmptyState';
import Button from '../../components/ui/Button';

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

export default function OrdersPage() {
  const { user } = useAuth();
  const { data: orders, isLoading } = useGetOrdersByUserIdQuery(user!.userId);

  if (isLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;

  if (!orders || orders.length === 0) {
    return (
      <div className="mx-auto max-w-7xl px-4 py-8">
        <EmptyState
          title="No orders yet"
          description="Start shopping to see your orders here."
          icon={<Package className="h-16 w-16" />}
          action={<Link to="/products"><Button>Browse Products</Button></Link>}
        />
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
      <h1 className="text-2xl font-bold text-gray-900 mb-8">My Orders</h1>
      <div className="space-y-4">
        {orders.map((order) => (
          <Link key={order.id} to={`/account/orders/${order.id}`} className="block rounded-xl border border-gray-200 bg-white p-5 hover:shadow-sm transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="font-medium text-gray-900">Order #{order.orderNumber}</p>
                <p className="text-sm text-gray-500">{formatDate(order.orderDate)} &middot; {order.items.length} items</p>
              </div>
              <div className="text-right">
                <p className="font-semibold text-gray-900">{formatCurrency(order.total)}</p>
                <Badge variant={statusVariant(order.orderStatus)}>
                  {order.orderStatus || 'PENDING'}
                </Badge>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}
