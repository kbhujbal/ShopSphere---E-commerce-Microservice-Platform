import { Bell, CheckCheck } from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import { useGetNotificationsQuery, useMarkAsReadMutation, useMarkAllAsReadMutation } from '../../features/notifications/notificationsApi';
import { formatDateTime } from '../../utils/formatDate';
import { cn } from '../../utils/cn';
import Button from '../../components/ui/Button';
import Spinner from '../../components/ui/Spinner';
import EmptyState from '../../components/ui/EmptyState';

export default function NotificationsPage() {
  const { user } = useAuth();
  const { data: notifications, isLoading } = useGetNotificationsQuery(user!.userId);
  const [markRead] = useMarkAsReadMutation();
  const [markAllRead] = useMarkAllAsReadMutation();

  if (isLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;

  if (!notifications || notifications.length === 0) {
    return (
      <div className="mx-auto max-w-3xl px-4 py-8">
        <EmptyState title="No notifications" description="You're all caught up!" icon={<Bell className="h-16 w-16" />} />
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-3xl px-4 py-8 sm:px-6">
      <div className="flex items-center justify-between mb-8">
        <h1 className="text-2xl font-bold text-gray-900">Notifications</h1>
        <Button variant="ghost" size="sm" onClick={() => markAllRead(user!.userId)}>
          <CheckCheck className="h-4 w-4 mr-1" /> Mark all read
        </Button>
      </div>
      <div className="space-y-2">
        {notifications.map((n) => (
          <div
            key={n.id}
            onClick={() => !n.read && markRead(n.id)}
            className={cn(
              'rounded-xl border p-4 cursor-pointer transition-colors',
              n.read ? 'border-gray-200 bg-white' : 'border-indigo-200 bg-indigo-50',
            )}
          >
            <div className="flex items-start justify-between">
              <div>
                <p className="font-medium text-gray-900">{n.title}</p>
                <p className="mt-1 text-sm text-gray-600">{n.content}</p>
              </div>
              {!n.read && <span className="h-2 w-2 rounded-full bg-indigo-600 flex-shrink-0 mt-2" />}
            </div>
            <p className="mt-2 text-xs text-gray-400">{formatDateTime(n.sentAt)}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
