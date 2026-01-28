import { useState } from 'react';
import { useGetPendingReviewsQuery, useApproveReviewMutation, useRejectReviewMutation } from '../../features/reviews/reviewsApi';
import StarRating from '../../components/ui/StarRating';
import Button from '../../components/ui/Button';
import Pagination from '../../components/ui/Pagination';
import Spinner from '../../components/ui/Spinner';
import EmptyState from '../../components/ui/EmptyState';

export default function ReviewModerationPage() {
  const [page, setPage] = useState(0);
  const { data, isLoading } = useGetPendingReviewsQuery({ page, size: 10 });
  const [approve] = useApproveReviewMutation();
  const [reject] = useRejectReviewMutation();

  if (isLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;

  if (!data || data.content.length === 0) {
    return (
      <div>
        <h1 className="text-2xl font-bold text-gray-900 mb-6">Review Moderation</h1>
        <EmptyState title="No pending reviews" description="All reviews have been moderated." />
      </div>
    );
  }

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Review Moderation</h1>
      <div className="space-y-4">
        {data.content.map((review) => (
          <div key={review.id} className="rounded-xl border border-gray-200 bg-white p-5">
            <div className="flex items-start justify-between">
              <div>
                <div className="flex items-center gap-2 mb-1">
                  <StarRating rating={review.rating} size="sm" />
                  <span className="font-medium text-gray-900">{review.title}</span>
                </div>
                <p className="text-sm text-gray-500 mb-2">Product: {review.productId} &middot; By: {review.userNickname || review.userId}</p>
                <p className="text-sm text-gray-700">{review.content}</p>
                {review.pros && <p className="text-sm mt-1"><span className="text-green-700 font-medium">Pros:</span> {review.pros}</p>}
                {review.cons && <p className="text-sm"><span className="text-red-700 font-medium">Cons:</span> {review.cons}</p>}
              </div>
              <div className="flex gap-2 flex-shrink-0 ml-4">
                <Button size="sm" onClick={() => approve({ id: review.id })}>Approve</Button>
                <Button size="sm" variant="danger" onClick={() => reject({ id: review.id, reason: 'Does not meet guidelines' })}>Reject</Button>
              </div>
            </div>
          </div>
        ))}
      </div>
      <Pagination currentPage={data.number} totalPages={data.totalPages} onPageChange={setPage} />
    </div>
  );
}
