export interface NotificationDTO {
  id: string;
  userId: string;
  type: string;
  title: string;
  content: string;
  status: string | null;
  sentAt: string | null;
  read: boolean;
  actionUrl: string | null;
  priority: string;
  category: string;
}
