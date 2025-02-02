import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { useGetCartQuery, useClearCartMutation } from '../../features/cart/cartApi';
import { useCreateOrderMutation } from '../../features/orders/ordersApi';
import { useProcessPaymentMutation } from '../../features/payments/paymentsApi';
import { useCreateNotificationMutation } from '../../features/notifications/notificationsApi';
import { formatCurrency } from '../../utils/formatCurrency';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import Spinner from '../../components/ui/Spinner';

export default function CheckoutPage() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const { data: cart, isLoading: cartLoading } = useGetCartQuery(user!.userId);
  const [createOrder] = useCreateOrderMutation();
  const [processPayment] = useProcessPaymentMutation();
  const [clearCart] = useClearCartMutation();
  const [createNotification] = useCreateNotificationMutation();

  const [step, setStep] = useState(1);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  const [shipping, setShipping] = useState({
    address: '', city: '', state: '', country: '', postalCode: '',
  });
  const [billing, setBilling] = useState({
    address: '', city: '', state: '', country: '', postalCode: '',
  });
  const [sameAsShipping, setSameAsShipping] = useState(true);
  const [paymentMethod] = useState('CREDIT_CARD');

  if (cartLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;
  if (!cart || cart.items.length === 0) {
    navigate('/cart');
    return null;
  }

  const effectiveBilling = sameAsShipping ? shipping : billing;

  const handlePlaceOrder = async () => {
    setSubmitting(true);
    setError('');
    try {
      const shippingStr = `${shipping.address}, ${shipping.city}, ${shipping.state} ${shipping.postalCode}, ${shipping.country}`;
      const billingStr = `${effectiveBilling.address}, ${effectiveBilling.city}, ${effectiveBilling.state} ${effectiveBilling.postalCode}, ${effectiveBilling.country}`;

      // 1. Create the order
      const order = await createOrder({
        userId: user!.userId,
        items: cart.items.map((item) => ({
          productId: item.productId,
          productName: item.productName,
          sku: item.sku,
          quantity: item.quantity,
          unitPrice: item.unitPrice,
          subtotal: item.subtotal,
          imageUrl: item.imageUrl,
        })),
        subtotal: cart.subtotal,
        tax: cart.tax,
        shippingCost: cart.shippingCost,
        total: cart.total,
        shippingAddress: shippingStr,
        billingAddress: billingStr,
        paymentMethod,
      }).unwrap();

      // 2. Process payment (non-blocking for order flow)
      try {
        await processPayment({
          orderId: order.id,
          userId: user!.userId,
          amount: order.total,
          currency: 'USD',
          paymentMethod,
          billingAddress: effectiveBilling.address,
          billingCity: effectiveBilling.city,
          billingState: effectiveBilling.state,
          billingCountry: effectiveBilling.country,
          billingPostalCode: effectiveBilling.postalCode,
          shippingAddress: shipping.address,
          shippingCity: shipping.city,
          shippingState: shipping.state,
          shippingCountry: shipping.country,
          shippingPostalCode: shipping.postalCode,
        }).unwrap();
      } catch {
        // Payment failed but order is created — continue
      }

      // 3. Clear the cart
      try {
        await clearCart(user!.userId).unwrap();
      } catch {
        // Cart clear failed — continue
      }

      // 4. Send notification
      try {
        await createNotification({
          userId: user!.userId,
          type: 'ORDER_CONFIRMATION',
          title: 'Order Placed Successfully',
          content: `Your order #${order.orderNumber || order.id} for ${formatCurrency(order.total)} has been placed and is being processed.`,
          priority: 'HIGH',
          category: 'ORDER',
          source: 'order-service',
          sourceId: order.id,
          recipient: user!.email || user!.username,
          actionUrl: `/orders/${order.id}`,
        }).unwrap();
      } catch {
        // Notification failed — continue
      }

      navigate(`/orders/${order.id}/confirmation`);
    } catch {
      setError('Failed to place order. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  const setAddr = (setter: typeof setShipping, field: string) => (e: React.ChangeEvent<HTMLInputElement>) =>
    setter((prev) => ({ ...prev, [field]: e.target.value }));

  const isShippingValid = Object.values(shipping).every(Boolean);
  const isBillingValid = sameAsShipping || Object.values(billing).every(Boolean);

  return (
    <div className="mx-auto max-w-3xl px-4 py-8 sm:px-6">
      <h1 className="text-2xl font-bold text-gray-900 mb-8">Checkout</h1>

      {/* Steps indicator */}
      <div className="flex items-center gap-2 mb-8">
        {['Shipping', 'Payment', 'Review'].map((label, i) => (
          <div key={label} className="flex items-center gap-2">
            <div className={`flex h-8 w-8 items-center justify-center rounded-full text-sm font-medium ${i + 1 <= step ? 'bg-indigo-600 text-white' : 'bg-gray-200 text-gray-500'}`}>
              {i + 1}
            </div>
            <span className={`text-sm ${i + 1 <= step ? 'text-gray-900 font-medium' : 'text-gray-500'}`}>{label}</span>
            {i < 2 && <div className="mx-2 h-px w-8 bg-gray-300" />}
          </div>
        ))}
      </div>

      {error && <div className="mb-4 rounded-lg bg-red-50 p-3 text-sm text-red-600">{error}</div>}

      {step === 1 && (
        <div className="rounded-xl border border-gray-200 bg-white p-6">
          <h2 className="text-lg font-semibold mb-4">Shipping Address</h2>
          <div className="space-y-4">
            <Input label="Address" value={shipping.address} onChange={setAddr(setShipping, 'address')} required />
            <div className="grid grid-cols-2 gap-4">
              <Input label="City" value={shipping.city} onChange={setAddr(setShipping, 'city')} required />
              <Input label="State" value={shipping.state} onChange={setAddr(setShipping, 'state')} required />
            </div>
            <div className="grid grid-cols-2 gap-4">
              <Input label="Country" value={shipping.country} onChange={setAddr(setShipping, 'country')} required />
              <Input label="Postal Code" value={shipping.postalCode} onChange={setAddr(setShipping, 'postalCode')} required />
            </div>
          </div>
          <div className="mt-6 flex justify-end">
            <Button onClick={() => setStep(2)} disabled={!isShippingValid}>Continue to Payment</Button>
          </div>
        </div>
      )}

      {step === 2 && (
        <div className="rounded-xl border border-gray-200 bg-white p-6">
          <h2 className="text-lg font-semibold mb-4">Payment & Billing</h2>
          <div className="mb-4 rounded-lg bg-gray-50 p-4 text-sm text-gray-600">
            Payment Method: Credit Card (simulated)
          </div>
          <label className="flex items-center gap-2 mb-4">
            <input type="checkbox" checked={sameAsShipping} onChange={(e) => setSameAsShipping(e.target.checked)} className="rounded border-gray-300" />
            <span className="text-sm text-gray-700">Billing address same as shipping</span>
          </label>
          {!sameAsShipping && (
            <div className="space-y-4">
              <Input label="Address" value={billing.address} onChange={setAddr(setBilling, 'address')} required />
              <div className="grid grid-cols-2 gap-4">
                <Input label="City" value={billing.city} onChange={setAddr(setBilling, 'city')} required />
                <Input label="State" value={billing.state} onChange={setAddr(setBilling, 'state')} required />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <Input label="Country" value={billing.country} onChange={setAddr(setBilling, 'country')} required />
                <Input label="Postal Code" value={billing.postalCode} onChange={setAddr(setBilling, 'postalCode')} required />
              </div>
            </div>
          )}
          <div className="mt-6 flex justify-between">
            <Button variant="ghost" onClick={() => setStep(1)}>Back</Button>
            <Button onClick={() => setStep(3)} disabled={!isBillingValid}>Review Order</Button>
          </div>
        </div>
      )}

      {step === 3 && (
        <div className="rounded-xl border border-gray-200 bg-white p-6">
          <h2 className="text-lg font-semibold mb-4">Order Review</h2>
          <div className="space-y-3 mb-6">
            {cart.items.map((item) => (
              <div key={item.productId} className="flex justify-between text-sm">
                <span>{item.productName} x {item.quantity}</span>
                <span className="font-medium">{formatCurrency(item.subtotal)}</span>
              </div>
            ))}
            <hr />
            <div className="flex justify-between text-sm"><span className="text-gray-500">Subtotal</span><span>{formatCurrency(cart.subtotal)}</span></div>
            <div className="flex justify-between text-sm"><span className="text-gray-500">Tax</span><span>{formatCurrency(cart.tax)}</span></div>
            <div className="flex justify-between text-sm"><span className="text-gray-500">Shipping</span><span>{formatCurrency(cart.shippingCost)}</span></div>
            <hr />
            <div className="flex justify-between font-semibold"><span>Total</span><span>{formatCurrency(cart.total)}</span></div>
          </div>
          <div className="text-sm text-gray-600 mb-6">
            <p><strong>Ship to:</strong> {shipping.address}, {shipping.city}, {shipping.state} {shipping.postalCode}</p>
          </div>
          <div className="flex justify-between">
            <Button variant="ghost" onClick={() => setStep(2)}>Back</Button>
            <Button onClick={handlePlaceOrder} disabled={submitting} size="lg">
              {submitting ? 'Placing Order...' : 'Place Order'}
            </Button>
          </div>
        </div>
      )}
    </div>
  );
}
