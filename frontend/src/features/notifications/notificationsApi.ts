import { baseApi } from '../../api/baseApi';
import type { ApiResponse } from '../../api/apiTypes';
import type { NotificationDTO } from './notificationsTypes';

const notificationsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getNotifications: builder.query<NotificationDTO[], string>({
      query: (userId) => `/notifications/user/${userId}`,
      transformResponse: (response: ApiResponse<NotificationDTO[]>) => response.data,
      providesTags: ['Notifications'],
    }),
    getUnreadNotifications: builder.query<NotificationDTO[], string>({
      query: (userId) => `/notifications/user/${userId}/unread`,
      transformResponse: (response: ApiResponse<NotificationDTO[]>) => response.data,
      providesTags: ['Notifications'],
    }),
    markAsRead: builder.mutation<NotificationDTO, string>({
      query: (id) => ({ url: `/notifications/${id}/read`, method: 'PUT' }),
      transformResponse: (response: ApiResponse<NotificationDTO>) => response.data,
      invalidatesTags: ['Notifications'],
    }),
    markAllAsRead: builder.mutation<void, string>({
      query: (userId) => ({
        url: `/notifications/user/${userId}/read-all`,
        method: 'PUT',
      }),
      invalidatesTags: ['Notifications'],
    }),
  }),
});

export const {
  useGetNotificationsQuery,
  useGetUnreadNotificationsQuery,
  useMarkAsReadMutation,
  useMarkAllAsReadMutation,
} = notificationsApi;
