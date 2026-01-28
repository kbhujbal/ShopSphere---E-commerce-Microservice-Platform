import { ThumbsUp, BadgeCheck } from 'lucide-react';
import type { ReviewDTO } from '../../../features/reviews/reviewsTypes';
import StarRating from '../../../components/ui/StarRating';
import { formatDate } from '../../../utils/formatDate';

interface ReviewCardProps {
  review: ReviewDTO;
}

export default function ReviewCard({ review }: ReviewCardProps) {
  return (
    <div className="rounded-xl border border-gray-200 bg-white p-5">
      <div className="flex items-start justify-between">
        <div>
          <div className="flex items-center gap-2">
            <StarRating rating={review.rating} size="sm" />
            <h4 className="font-medium text-gray-900">{review.title}</h4>
          </div>
          <div className="mt-1 flex items-center gap-2 text-sm text-gray-500">
            <span>{review.anonymous ? 'Anonymous' : review.userNickname || 'User'}</span>
            <span>&middot;</span>
            <span>{formatDate(review.approvedAt || review.purchaseDate)}</span>
            {review.verifiedPurchase && (
              <span className="flex items-center gap-1 text-green-600">
                <BadgeCheck className="h-3.5 w-3.5" /> Verified
              </span>
            )}
          </div>
        </div>
      </div>
      <p className="mt-3 text-sm text-gray-700 leading-relaxed">{review.content}</p>
      {(review.pros || review.cons) && (
        <div className="mt-3 flex gap-6 text-sm">
          {review.pros && (
            <div>
              <span className="font-medium text-green-700">Pros:</span>{' '}
              <span className="text-gray-600">{review.pros}</span>
            </div>
          )}
          {review.cons && (
            <div>
              <span className="font-medium text-red-700">Cons:</span>{' '}
              <span className="text-gray-600">{review.cons}</span>
            </div>
          )}
        </div>
      )}
      <div className="mt-3 flex items-center gap-4 text-sm text-gray-500">
        <span className="flex items-center gap-1">
          <ThumbsUp className="h-3.5 w-3.5" /> {review.likes}
        </span>
        <span>Helpful ({review.helpfulCount})</span>
      </div>
    </div>
  );
}
