import { baseApi } from '../../api/baseApi';
import type { OrderDTO } from './ordersTypes';

const ordersApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    createOrder: builder.mutation<OrderDTO, Partial<OrderDTO>>({
      query: (body) => ({ url: '/orders', method: 'POST', body }),
      invalidatesTags: ['Orders', 'Cart'],
    }),
    getOrderById: builder.query<OrderDTO, string>({
      query: (id) => `/orders/${id}`,
      providesTags: (_result, _error, id) => [{ type: 'Order', id }],
    }),
    getOrdersByUserId: builder.query<OrderDTO[], string>({
      query: (userId) => `/orders/user/${userId}`,
      providesTags: ['Orders'],
    }),
    updateOrderStatus: builder.mutation<OrderDTO, { id: string; orderStatus: string }>({
      query: ({ id, orderStatus }) => ({
        url: `/orders/${id}/status?orderStatus=${orderStatus}`,
        method: 'PATCH',
      }),
      invalidatesTags: (_result, _error, { id }) => ['Orders', { type: 'Order', id }],
    }),
    getOrdersByStatus: builder.query<OrderDTO[], string>({
      query: (status) => `/orders/status/${status}`,
      providesTags: ['Orders'],
    }),
  }),
});

export const {
  useCreateOrderMutation,
  useGetOrderByIdQuery,
  useGetOrdersByUserIdQuery,
  useUpdateOrderStatusMutation,
  useGetOrdersByStatusQuery,
} = ordersApi;
