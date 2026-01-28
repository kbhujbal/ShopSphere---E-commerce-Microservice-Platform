import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useGetOrdersByStatusQuery, useUpdateOrderStatusMutation } from '../../features/orders/ordersApi';
import { formatCurrency } from '../../utils/formatCurrency';
import { formatDate } from '../../utils/formatDate';
import Badge from '../../components/ui/Badge';
import Spinner from '../../components/ui/Spinner';
import Select from '../../components/ui/Select';

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

export default function OrdersListPage() {
  const [filter, setFilter] = useState('PENDING');
  const { data: orders, isLoading } = useGetOrdersByStatusQuery(filter);
  const [updateStatus] = useUpdateOrderStatusMutation();

  const handleStatusChange = async (orderId: string, newStatus: string) => {
    await updateStatus({ id: orderId, orderStatus: newStatus });
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Orders</h1>
        <div className="flex gap-2">
          {statuses.map((s) => (
            <button
              key={s}
              onClick={() => setFilter(s)}
              className={`rounded-lg px-3 py-1.5 text-xs font-medium transition-colors ${filter === s ? 'bg-indigo-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
            >
              {s}
            </button>
          ))}
        </div>
      </div>

      {isLoading ? (
        <div className="flex justify-center py-20"><Spinner size="lg" /></div>
      ) : (
        <div className="overflow-x-auto rounded-xl border border-gray-200 bg-white">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Order #</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Date</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Items</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Total</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Update</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {orders?.map((o) => (
                <tr key={o.id} className="hover:bg-gray-50">
                  <td className="px-6 py-4 text-sm">
                    <Link to={`/admin/orders/${o.id}`} className="font-medium text-indigo-600 hover:text-indigo-500">
                      {o.orderNumber}
                    </Link>
                  </td>
                  <td className="px-6 py-4 text-sm text-gray-500">{formatDate(o.orderDate)}</td>
                  <td className="px-6 py-4 text-sm text-gray-500">{o.items.length}</td>
                  <td className="px-6 py-4 text-sm font-medium">{formatCurrency(o.total)}</td>
                  <td className="px-6 py-4">
                    <Badge variant={statusVariant(o.orderStatus || 'PENDING')}>{o.orderStatus || 'PENDING'}</Badge>
                  </td>
                  <td className="px-6 py-4">
                    <Select
                      options={statuses.map((s) => ({ value: s, label: s }))}
                      value={o.orderStatus || 'PENDING'}
                      onChange={(e) => handleStatusChange(o.id, e.target.value)}
                      className="w-36"
                    />
                  </td>
                </tr>
              ))}
              {orders?.length === 0 && (
                <tr><td colSpan={6} className="px-6 py-8 text-center text-sm text-gray-500">No orders with this status</td></tr>
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
