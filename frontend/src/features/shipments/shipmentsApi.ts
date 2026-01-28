import { baseApi } from '../../api/baseApi';
import type { ApiResponse } from '../../api/apiTypes';
import type { ShipmentDTO } from './shipmentsTypes';

const shipmentsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getShipmentByOrderId: builder.query<ShipmentDTO, string>({
      query: (orderId) => `/shipments/order/${orderId}`,
      transformResponse: (response: ApiResponse<ShipmentDTO>) => response.data,
      providesTags: ['Shipment'],
    }),
    getShipmentByTracking: builder.query<ShipmentDTO, string>({
      query: (trackingNumber) => `/shipments/tracking/${trackingNumber}`,
      transformResponse: (response: ApiResponse<ShipmentDTO>) => response.data,
      providesTags: ['Shipment'],
    }),
    updateShipmentStatus: builder.mutation<ShipmentDTO, { id: string; status: string }>({
      query: ({ id, status }) => ({
        url: `/shipments/${id}/status?status=${status}`,
        method: 'PUT',
      }),
      transformResponse: (response: ApiResponse<ShipmentDTO>) => response.data,
      invalidatesTags: ['Shipment'],
    }),
  }),
});

export const {
  useGetShipmentByOrderIdQuery,
  useGetShipmentByTrackingQuery,
  useUpdateShipmentStatusMutation,
} = shipmentsApi;
