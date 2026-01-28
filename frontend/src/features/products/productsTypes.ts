export interface ProductDTO {
  id: string;
  name: string;
  description: string;
  category: string;
  price: number;
  stockQuantity: number;
  images: string[];
  brand: string;
  rating: number | null;
  reviewCount: number | null;
  active: boolean;
  sku: string;
  tags: string[];
  specifications: string[];
}
