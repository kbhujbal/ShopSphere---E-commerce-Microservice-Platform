import { Link } from 'react-router-dom';
import { Package } from 'lucide-react';
import type { ProductDTO } from '../../../features/products/productsTypes';
import { formatCurrency } from '../../../utils/formatCurrency';
import StarRating from '../../../components/ui/StarRating';

interface ProductCardProps {
  product: ProductDTO;
}

export default function ProductCard({ product }: ProductCardProps) {
  return (
    <Link to={`/products/${product.id}`} className="group">
      <div className="overflow-hidden rounded-xl border border-gray-200 bg-white transition-shadow hover:shadow-md">
        <div className="aspect-square bg-gray-100 flex items-center justify-center overflow-hidden">
          {product.images?.[0] ? (
            <img src={product.images[0]} alt={product.name} className="h-full w-full object-cover group-hover:scale-105 transition-transform" />
          ) : (
            <Package className="h-16 w-16 text-gray-300" />
          )}
        </div>
        <div className="p-4">
          <p className="text-xs text-gray-500 uppercase tracking-wide">{product.brand}</p>
          <h3 className="mt-1 font-medium text-gray-900 line-clamp-1 group-hover:text-indigo-600">
            {product.name}
          </h3>
          <div className="mt-1 flex items-center gap-2">
            <StarRating rating={product.rating ?? 0} size="sm" />
            {product.reviewCount != null && product.reviewCount > 0 && (
              <span className="text-xs text-gray-500">({product.reviewCount})</span>
            )}
          </div>
          <div className="mt-2 flex items-center justify-between">
            <p className="text-lg font-semibold text-gray-900">{formatCurrency(product.price)}</p>
            {product.stockQuantity <= 0 && (
              <span className="text-xs font-medium text-red-600">Out of Stock</span>
            )}
          </div>
        </div>
      </div>
    </Link>
  );
}
