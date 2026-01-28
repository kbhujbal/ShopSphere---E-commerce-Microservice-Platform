import { createBrowserRouter } from 'react-router-dom';
import StoreLayout from '../components/layout/StoreLayout';
import AdminLayout from '../components/layout/AdminLayout';
import ProtectedRoute from './ProtectedRoute';
import AdminRoute from './AdminRoute';
import PublicOnlyRoute from './PublicOnlyRoute';

import HomePage from '../pages/store/HomePage';
import ProductListPage from '../pages/store/ProductListPage';
import ProductDetailPage from '../pages/store/ProductDetailPage';
import CartPage from '../pages/store/CartPage';
import CheckoutPage from '../pages/store/CheckoutPage';
import OrderConfirmationPage from '../pages/store/OrderConfirmationPage';
import OrdersPage from '../pages/store/OrdersPage';
import OrderDetailPage from '../pages/store/OrderDetailPage';
import ProfilePage from '../pages/store/ProfilePage';
import NotificationsPage from '../pages/store/NotificationsPage';

import LoginPage from '../pages/auth/LoginPage';
import RegisterPage from '../pages/auth/RegisterPage';

import DashboardPage from '../pages/admin/DashboardPage';
import AdminProductsListPage from '../pages/admin/ProductsListPage';
import AdminProductFormPage from '../pages/admin/ProductFormPage';
import AdminOrdersListPage from '../pages/admin/OrdersListPage';
import AdminOrderDetailPage from '../pages/admin/AdminOrderDetailPage';
import AdminUsersListPage from '../pages/admin/UsersListPage';
import ReviewModerationPage from '../pages/admin/ReviewModerationPage';
import ShipmentsPage from '../pages/admin/ShipmentsPage';

import NotFoundPage from '../pages/NotFoundPage';

export const router = createBrowserRouter([
  {
    element: <StoreLayout />,
    children: [
      { path: '/', element: <HomePage /> },
      { path: '/products', element: <ProductListPage /> },
      { path: '/products/:id', element: <ProductDetailPage /> },

      {
        element: <PublicOnlyRoute />,
        children: [
          { path: '/login', element: <LoginPage /> },
          { path: '/register', element: <RegisterPage /> },
        ],
      },

      {
        element: <ProtectedRoute />,
        children: [
          { path: '/cart', element: <CartPage /> },
          { path: '/checkout', element: <CheckoutPage /> },
          { path: '/orders/:id/confirmation', element: <OrderConfirmationPage /> },
          { path: '/account/orders', element: <OrdersPage /> },
          { path: '/account/orders/:id', element: <OrderDetailPage /> },
          { path: '/account/profile', element: <ProfilePage /> },
          { path: '/account/notifications', element: <NotificationsPage /> },
        ],
      },
    ],
  },

  {
    element: <AdminRoute />,
    children: [
      {
        element: <AdminLayout />,
        children: [
          { path: '/admin', element: <DashboardPage /> },
          { path: '/admin/products', element: <AdminProductsListPage /> },
          { path: '/admin/products/new', element: <AdminProductFormPage /> },
          { path: '/admin/products/:id/edit', element: <AdminProductFormPage /> },
          { path: '/admin/orders', element: <AdminOrdersListPage /> },
          { path: '/admin/orders/:id', element: <AdminOrderDetailPage /> },
          { path: '/admin/users', element: <AdminUsersListPage /> },
          { path: '/admin/reviews', element: <ReviewModerationPage /> },
          { path: '/admin/shipments', element: <ShipmentsPage /> },
        ],
      },
    ],
  },

  { path: '*', element: <NotFoundPage /> },
]);
