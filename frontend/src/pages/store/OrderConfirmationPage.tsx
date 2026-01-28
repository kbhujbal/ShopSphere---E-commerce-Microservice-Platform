import { Link, useParams } from 'react-router-dom';
import { CheckCircle } from 'lucide-react';
import { useGetOrderByIdQuery } from '../../features/orders/ordersApi';
import { formatCurrency } from '../../utils/formatCurrency';
import Button from '../../components/ui/Button';
import Spinner from '../../components/ui/Spinner';

export default function OrderConfirmationPage() {
  const { id } = useParams<{ id: string }>();
  const { data: order, isLoading } = useGetOrderByIdQuery(id!);

  if (isLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;

  return (
    <div className="mx-auto max-w-2xl px-4 py-16 text-center">
      <CheckCircle className="mx-auto h-16 w-16 text-green-500" />
      <h1 className="mt-4 text-2xl font-bold text-gray-900">Order Confirmed!</h1>
      <p className="mt-2 text-gray-500">Thank you for your purchase.</p>

      {order && (
        <div className="mt-8 rounded-xl border border-gray-200 bg-white p-6 text-left">
          <div className="grid grid-cols-2 gap-4 text-sm">
            <div>
              <p className="text-gray-500">Order Number</p>
              <p className="font-medium">{order.orderNumber}</p>
            </div>
            <div>
              <p className="text-gray-500">Total</p>
              <p className="font-medium">{formatCurrency(order.total)}</p>
            </div>
            <div>
              <p className="text-gray-500">Status</p>
              <p className="font-medium capitalize">{order.orderStatus?.toLowerCase()}</p>
            </div>
            <div>
              <p className="text-gray-500">Items</p>
              <p className="font-medium">{order.items.length} items</p>
            </div>
          </div>
        </div>
      )}

      <div className="mt-8 flex justify-center gap-4">
        <Link to={`/account/orders/${id}`}>
          <Button>View Order Details</Button>
        </Link>
        <Link to="/products">
          <Button variant="secondary">Continue Shopping</Button>
        </Link>
      </div>
    </div>
  );
}
