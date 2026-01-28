import { baseApi } from '../../api/baseApi';
import type { ApiResponse } from '../../api/apiTypes';
import type { UserDTO } from './usersTypes';

const usersApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getAllUsers: builder.query<UserDTO[], void>({
      query: () => '/users',
      transformResponse: (response: ApiResponse<UserDTO[]>) => response.data,
      providesTags: ['Users'],
    }),
    getUserById: builder.query<UserDTO, string>({
      query: (id) => `/users/${id}`,
      transformResponse: (response: ApiResponse<UserDTO>) => response.data,
      providesTags: (_result, _error, id) => [{ type: 'User', id }],
    }),
    updateUser: builder.mutation<UserDTO, { id: string; body: Partial<UserDTO> }>({
      query: ({ id, body }) => ({ url: `/users/${id}`, method: 'PUT', body }),
      transformResponse: (response: ApiResponse<UserDTO>) => response.data,
      invalidatesTags: (_result, _error, { id }) => ['Users', { type: 'User', id }],
    }),
    changePassword: builder.mutation<void, { id: string; oldPassword: string; newPassword: string }>({
      query: ({ id, oldPassword, newPassword }) => ({
        url: `/users/${id}/password?oldPassword=${encodeURIComponent(oldPassword)}&newPassword=${encodeURIComponent(newPassword)}`,
        method: 'PUT',
      }),
    }),
    deleteUser: builder.mutation<void, string>({
      query: (id) => ({ url: `/users/${id}`, method: 'DELETE' }),
      invalidatesTags: ['Users'],
    }),
  }),
});

export const {
  useGetAllUsersQuery,
  useGetUserByIdQuery,
  useUpdateUserMutation,
  useChangePasswordMutation,
  useDeleteUserMutation,
} = usersApi;
