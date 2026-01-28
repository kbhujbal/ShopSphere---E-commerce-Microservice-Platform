import { baseApi } from '../../api/baseApi';
import type { ApiResponse, Page } from '../../api/apiTypes';
import type { ProductDTO } from './productsTypes';

const productsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getProducts: builder.query<ProductDTO[], void>({
      query: () => '/products/active',
      transformResponse: (response: ApiResponse<ProductDTO[]>) => response.data,
      providesTags: ['Products'],
    }),
    getAllProducts: builder.query<ProductDTO[], void>({
      query: () => '/products',
      transformResponse: (response: ApiResponse<ProductDTO[]>) => response.data,
      providesTags: ['Products'],
    }),
    getProductById: builder.query<ProductDTO, string>({
      query: (id) => `/products/${id}`,
      transformResponse: (response: ApiResponse<ProductDTO>) => response.data,
      providesTags: (_result, _error, id) => [{ type: 'Product', id }],
    }),
    getProductsPage: builder.query<Page<ProductDTO>, { page: number; size: number }>({
      query: ({ page, size }) => `/products/page?page=${page}&size=${size}`,
      transformResponse: (response: ApiResponse<Page<ProductDTO>>) => response.data,
      providesTags: ['Products'],
    }),
    getProductsByCategory: builder.query<ProductDTO[], string>({
      query: (category) => `/products/category/${category}`,
      transformResponse: (response: ApiResponse<ProductDTO[]>) => response.data,
      providesTags: ['Products'],
    }),
    getProductsByBrand: builder.query<ProductDTO[], string>({
      query: (brand) => `/products/brand/${brand}`,
      transformResponse: (response: ApiResponse<ProductDTO[]>) => response.data,
      providesTags: ['Products'],
    }),
    createProduct: builder.mutation<ProductDTO, Partial<ProductDTO>>({
      query: (body) => ({ url: '/products', method: 'POST', body }),
      transformResponse: (response: ApiResponse<ProductDTO>) => response.data,
      invalidatesTags: ['Products'],
    }),
    updateProduct: builder.mutation<ProductDTO, { id: string; body: Partial<ProductDTO> }>({
      query: ({ id, body }) => ({ url: `/products/${id}`, method: 'PUT', body }),
      transformResponse: (response: ApiResponse<ProductDTO>) => response.data,
      invalidatesTags: (_result, _error, { id }) => ['Products', { type: 'Product', id }],
    }),
    deleteProduct: builder.mutation<void, string>({
      query: (id) => ({ url: `/products/${id}`, method: 'DELETE' }),
      invalidatesTags: ['Products'],
    }),
    updateStock: builder.mutation<void, { id: string; quantity: number }>({
      query: ({ id, quantity }) => ({
        url: `/products/${id}/stock?quantity=${quantity}`,
        method: 'PUT',
      }),
      invalidatesTags: (_result, _error, { id }) => [{ type: 'Product', id }],
    }),
  }),
});

export const {
  useGetProductsQuery,
  useGetAllProductsQuery,
  useGetProductByIdQuery,
  useGetProductsPageQuery,
  useGetProductsByCategoryQuery,
  useGetProductsByBrandQuery,
  useCreateProductMutation,
  useUpdateProductMutation,
  useDeleteProductMutation,
  useUpdateStockMutation,
} = productsApi;
