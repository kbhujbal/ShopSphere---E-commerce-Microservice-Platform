import { Link } from 'react-router-dom';
import { Trash2, Minus, Plus, ShoppingBag } from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import { useGetCartQuery, useUpdateCartItemQuantityMutation, useRemoveCartItemMutation } from '../../features/cart/cartApi';
import { formatCurrency } from '../../utils/formatCurrency';
import Button from '../../components/ui/Button';
import Spinner from '../../components/ui/Spinner';
import EmptyState from '../../components/ui/EmptyState';

// cart component
export default function CartPage() {
  const { user } = useAuth();
  const { data: cart, isLoading } = useGetCartQuery(user!.userId);
  const [updateQty] = useUpdateCartItemQuantityMutation();
  const [removeItem] = useRemoveCartItemMutation();

  if (isLoading) {
    return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;
  }

  if (!cart || cart.items.length === 0) {
    return (
      <div className="mx-auto max-w-7xl px-4 py-8">
        <EmptyState
          title="Your cart is empty"
          description="Add some products to get started."
          icon={<ShoppingBag className="h-16 w-16" />}
          action={<Link to="/products"><Button>Browse Products</Button></Link>}
        />
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
      <h1 className="text-2xl font-bold text-gray-900 mb-8">Shopping Cart</h1>
      <div className="grid grid-cols-1 gap-8 lg:grid-cols-3">
        <div className="lg:col-span-2 space-y-4">
          {cart.items.map((item) => (
            <div key={item.productId} className="flex gap-4 rounded-xl border border-gray-200 bg-white p-4">
              <div className="h-24 w-24 flex-shrink-0 rounded-lg bg-gray-100 flex items-center justify-center overflow-hidden">
                {item.imageUrl ? (
                  <img src={item.imageUrl} alt={item.productName} className="h-full w-full object-cover" />
                ) : (
                  <ShoppingBag className="h-8 w-8 text-gray-300" />
                )}
              </div>
              <div className="flex-1 min-w-0">
                <Link to={`/products/${item.productId}`} className="font-medium text-gray-900 hover:text-indigo-600">
                  {item.productName}
                </Link>
                <p className="text-sm text-gray-500">SKU: {item.sku}</p>
                <p className="mt-1 font-medium text-gray-900">{formatCurrency(item.unitPrice)}</p>
              </div>
              <div className="flex flex-col items-end gap-2">
                <button onClick={() => removeItem({ userId: user!.userId, productId: item.productId })} className="rounded-lg p-1 text-gray-400 hover:text-red-500">
                  <Trash2 className="h-4 w-4" />
                </button>
                <div className="flex items-center rounded-lg border border-gray-300">
                  <button onClick={() => updateQty({ userId: user!.userId, productId: item.productId, quantity: Math.max(1, item.quantity - 1) })} className="px-2 py-1 hover:bg-gray-50">
                    <Minus className="h-3 w-3" />
                  </button>
                  <span className="w-8 text-center text-sm">{item.quantity}</span>
                  <button onClick={() => updateQty({ userId: user!.userId, productId: item.productId, quantity: item.quantity + 1 })} className="px-2 py-1 hover:bg-gray-50">
                    <Plus className="h-3 w-3" />
                  </button>
                </div>
                <p className="text-sm font-medium text-gray-900">{formatCurrency(item.subtotal)}</p>
              </div>
            </div>
          ))}
        </div>

        <div className="rounded-xl border border-gray-200 bg-white p-6 h-fit">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">Order Summary</h2>
          <div className="space-y-2 text-sm">
            <div className="flex justify-between"><span className="text-gray-500">Subtotal</span><span>{formatCurrency(cart.subtotal)}</span></div>
            <div className="flex justify-between"><span className="text-gray-500">Tax</span><span>{formatCurrency(cart.tax)}</span></div>
            <div className="flex justify-between"><span className="text-gray-500">Shipping</span><span>{formatCurrency(cart.shippingCost)}</span></div>
            {cart.discount != null && cart.discount > 0 && (
              <div className="flex justify-between text-green-600"><span>Discount</span><span>-{formatCurrency(cart.discount)}</span></div>
            )}
            <hr className="my-2" />
            <div className="flex justify-between text-base font-semibold">
              <span>Total</span><span>{formatCurrency(cart.total)}</span>
            </div>
          </div>
          <Link to="/checkout" className="mt-6 block">
            <Button className="w-full" size="lg">Proceed to Checkout</Button>
          </Link>
        </div>
      </div>
    </div>
  );
}
