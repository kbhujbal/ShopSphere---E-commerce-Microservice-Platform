import { baseApi } from '../../api/baseApi';
import type { ApiResponse } from '../../api/apiTypes';
import type { CartDTO, CartItemDTO } from './cartTypes';

const cartApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getCart: builder.query<CartDTO | null, string>({
      query: (userId) => `/carts/user/${userId}`,
      transformResponse: (response: ApiResponse<CartDTO>) => response.data,
      transformErrorResponse: (response) => {
        if (response.status === 404) return null;
        return response;
      },
      providesTags: ['Cart'],
    }),
    addToCart: builder.mutation<CartDTO, { userId: string; item: CartItemDTO }>({
      query: ({ userId, item }) => ({
        url: `/carts/user/${userId}/items`,
        method: 'POST',
        body: item,
      }),
      transformResponse: (response: ApiResponse<CartDTO>) => response.data,
      invalidatesTags: ['Cart'],
    }),
    updateCartItemQuantity: builder.mutation<CartDTO, { userId: string; productId: string; quantity: number }>({
      query: ({ userId, productId, quantity }) => ({
        url: `/carts/user/${userId}/items/${productId}?quantity=${quantity}`,
        method: 'PUT',
      }),
      transformResponse: (response: ApiResponse<CartDTO>) => response.data,
      invalidatesTags: ['Cart'],
    }),
    removeCartItem: builder.mutation<CartDTO, { userId: string; productId: string }>({
      query: ({ userId, productId }) => ({
        url: `/carts/user/${userId}/items/${productId}`,
        method: 'DELETE',
      }),
      transformResponse: (response: ApiResponse<CartDTO>) => response.data,
      invalidatesTags: ['Cart'],
    }),
    clearCart: builder.mutation<void, string>({
      query: (userId) => ({
        url: `/carts/user/${userId}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Cart'],
    }),
    applyCoupon: builder.mutation<CartDTO, { userId: string; couponCode: string }>({
      query: ({ userId, couponCode }) => ({
        url: `/carts/user/${userId}/coupon?couponCode=${couponCode}`,
        method: 'POST',
      }),
      transformResponse: (response: ApiResponse<CartDTO>) => response.data,
      invalidatesTags: ['Cart'],
    }),
  }),
});

export const {
  useGetCartQuery,
  useAddToCartMutation,
  useUpdateCartItemQuantityMutation,
  useRemoveCartItemMutation,
  useClearCartMutation,
  useApplyCouponMutation,
} = cartApi;
