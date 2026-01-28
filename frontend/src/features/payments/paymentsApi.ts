import { baseApi } from '../../api/baseApi';
import type { ApiResponse } from '../../api/apiTypes';
import type { PaymentDTO } from './paymentsTypes';

const paymentsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    processPayment: builder.mutation<PaymentDTO, Partial<PaymentDTO>>({
      query: (body) => ({ url: '/payments/process', method: 'POST', body }),
      transformResponse: (response: ApiResponse<PaymentDTO>) => response.data,
      invalidatesTags: ['Payment'],
    }),
    getPaymentByOrderId: builder.query<PaymentDTO, string>({
      query: (orderId) => `/payments/order/${orderId}`,
      transformResponse: (response: ApiResponse<PaymentDTO>) => response.data,
      providesTags: ['Payment'],
    }),
    refundPayment: builder.mutation<PaymentDTO, { id: string; reason: string }>({
      query: ({ id, reason }) => ({
        url: `/payments/${id}/refund?reason=${encodeURIComponent(reason)}`,
        method: 'POST',
      }),
      transformResponse: (response: ApiResponse<PaymentDTO>) => response.data,
      invalidatesTags: ['Payment'],
    }),
  }),
});

export const {
  useProcessPaymentMutation,
  useGetPaymentByOrderIdQuery,
  useRefundPaymentMutation,
} = paymentsApi;
