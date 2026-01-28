import { baseApi } from '../../api/baseApi';
import type { Page } from '../../api/apiTypes';
import type { ReviewDTO } from './reviewsTypes';

const reviewsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getReviewsByProductId: builder.query<Page<ReviewDTO>, { productId: string; page: number; size: number }>({
      query: ({ productId, page, size }) =>
        `/reviews/product/${productId}?page=${page}&size=${size}`,
      providesTags: (_result, _error, { productId }) => [{ type: 'Reviews', id: productId }],
    }),
    getAverageRating: builder.query<number, string>({
      query: (productId) => `/reviews/product/${productId}/average-rating`,
    }),
    createReview: builder.mutation<ReviewDTO, Partial<ReviewDTO>>({
      query: (body) => ({ url: '/reviews', method: 'POST', body }),
      invalidatesTags: ['Reviews'],
    }),
    markHelpful: builder.mutation<ReviewDTO, string>({
      query: (id) => ({ url: `/reviews/${id}/helpful`, method: 'PUT' }),
      invalidatesTags: ['Reviews'],
    }),
    likeReview: builder.mutation<ReviewDTO, string>({
      query: (id) => ({ url: `/reviews/${id}/like`, method: 'PUT' }),
      invalidatesTags: ['Reviews'],
    }),
    getPendingReviews: builder.query<Page<ReviewDTO>, { page: number; size: number }>({
      query: ({ page, size }) => `/reviews/pending?page=${page}&size=${size}`,
      providesTags: ['Reviews'],
    }),
    approveReview: builder.mutation<ReviewDTO, { id: string; moderatorNotes?: string }>({
      query: ({ id, moderatorNotes }) => ({
        url: `/reviews/${id}/approve`,
        method: 'PUT',
        body: moderatorNotes || '',
      }),
      invalidatesTags: ['Reviews'],
    }),
    rejectReview: builder.mutation<ReviewDTO, { id: string; reason: string }>({
      query: ({ id, reason }) => ({
        url: `/reviews/${id}/reject`,
        method: 'PUT',
        body: reason,
      }),
      invalidatesTags: ['Reviews'],
    }),
  }),
});

export const {
  useGetReviewsByProductIdQuery,
  useGetAverageRatingQuery,
  useCreateReviewMutation,
  useMarkHelpfulMutation,
  useLikeReviewMutation,
  useGetPendingReviewsQuery,
  useApproveReviewMutation,
  useRejectReviewMutation,
} = reviewsApi;
