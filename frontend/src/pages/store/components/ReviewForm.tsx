import { useState } from 'react';
import { Star } from 'lucide-react';
import { useCreateReviewMutation } from '../../../features/reviews/reviewsApi';
import Button from '../../../components/ui/Button';
import Input from '../../../components/ui/Input';

interface ReviewFormProps {
  productId: string;
  userId: string;
  userNickname: string;
  onSuccess: () => void;
}

export default function ReviewForm({ productId, userId, userNickname, onSuccess }: ReviewFormProps) {
  const [createReview, { isLoading }] = useCreateReviewMutation();
  const [rating, setRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [pros, setPros] = useState('');
  const [cons, setCons] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (rating === 0) {
      setError('Please select a rating');
      return;
    }
    setError('');
    try {
      await createReview({
        userId,
        productId,
        orderId: 'direct-review',
        orderItemId: 'direct-review',
        rating,
        title,
        content,
        userNickname,
        pros: pros || null,
        cons: cons || null,
        verifiedPurchase: false,
        anonymous: false,
        images: [],
        tags: [],
      }).unwrap();
      setRating(0);
      setTitle('');
      setContent('');
      setPros('');
      setCons('');
      onSuccess();
    } catch {
      setError('Failed to submit review. Please try again.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="rounded-xl border border-gray-200 bg-white p-6">
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Write a Review</h3>

      {error && <div className="mb-4 rounded-lg bg-red-50 p-3 text-sm text-red-600">{error}</div>}

      {/* Star Rating Selector */}
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-1">Rating</label>
        <div className="flex gap-1">
          {[1, 2, 3, 4, 5].map((star) => (
            <button
              key={star}
              type="button"
              onClick={() => setRating(star)}
              onMouseEnter={() => setHoverRating(star)}
              onMouseLeave={() => setHoverRating(0)}
              className="p-0.5"
            >
              <Star
                className={`h-7 w-7 transition-colors ${
                  star <= (hoverRating || rating)
                    ? 'fill-yellow-400 text-yellow-400'
                    : 'text-gray-300'
                }`}
              />
            </button>
          ))}
          <span className="ml-2 text-sm text-gray-500 self-center">
            {rating > 0 ? `${rating}/5` : 'Select a rating'}
          </span>
        </div>
      </div>

      <div className="space-y-4">
        <Input
          label="Review Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Summarize your experience"
          required
        />

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Your Review</label>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            placeholder="What did you like or dislike about this product?"
            rows={4}
            required
            className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 focus:outline-none"
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <Input
            label="Pros (optional)"
            value={pros}
            onChange={(e) => setPros(e.target.value)}
            placeholder="What you liked"
          />
          <Input
            label="Cons (optional)"
            value={cons}
            onChange={(e) => setCons(e.target.value)}
            placeholder="What could improve"
          />
        </div>
      </div>

      <div className="mt-6">
        <Button type="submit" disabled={isLoading || rating === 0 || !title || !content}>
          {isLoading ? 'Submitting...' : 'Submit Review'}
        </Button>
      </div>
    </form>
  );
}
