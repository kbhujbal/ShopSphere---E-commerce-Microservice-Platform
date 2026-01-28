import { baseApi } from '../../api/baseApi';
import type { ApiResponse } from '../../api/apiTypes';
import type { AuthResponse, LoginRequest, RegisterRequest } from './authTypes';

const authApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    login: builder.mutation<AuthResponse, LoginRequest>({
      query: (body) => ({ url: '/auth/login', method: 'POST', body }),
      transformResponse: (response: ApiResponse<AuthResponse>) => response.data,
    }),
    register: builder.mutation<AuthResponse, RegisterRequest>({
      query: (body) => ({ url: '/auth/register', method: 'POST', body }),
      transformResponse: (response: ApiResponse<AuthResponse>) => response.data,
    }),
  }),
});

export const { useLoginMutation, useRegisterMutation } = authApi;
