import { useState, useEffect } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { useGetUserByIdQuery, useUpdateUserMutation, useChangePasswordMutation } from '../../features/users/usersApi';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import Card from '../../components/ui/Card';
import Spinner from '../../components/ui/Spinner';

export default function ProfilePage() {
  const { user } = useAuth();
  const { data: userData, isLoading } = useGetUserByIdQuery(user!.userId);
  const [updateUser, { isLoading: saving }] = useUpdateUserMutation();
  const [changePassword, { isLoading: changingPw }] = useChangePasswordMutation();

  const [form, setForm] = useState({ firstName: '', lastName: '', email: '', phoneNumber: '' });
  const [pwForm, setPwForm] = useState({ oldPassword: '', newPassword: '' });
  const [message, setMessage] = useState('');
  const [pwMessage, setPwMessage] = useState('');

  useEffect(() => {
    if (userData) {
      setForm({
        firstName: userData.firstName,
        lastName: userData.lastName,
        email: userData.email,
        phoneNumber: userData.phoneNumber,
      });
    }
  }, [userData]);

  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await updateUser({ id: user!.userId, body: form }).unwrap();
      setMessage('Profile updated successfully.');
      setTimeout(() => setMessage(''), 3000);
    } catch {
      setMessage('Failed to update profile.');
    }
  };

  const handleChangePassword = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await changePassword({ id: user!.userId, ...pwForm }).unwrap();
      setPwMessage('Password changed successfully.');
      setPwForm({ oldPassword: '', newPassword: '' });
      setTimeout(() => setPwMessage(''), 3000);
    } catch {
      setPwMessage('Failed to change password.');
    }
  };

  if (isLoading) return <div className="flex justify-center py-20"><Spinner size="lg" /></div>;

  return (
    <div className="mx-auto max-w-2xl px-4 py-8 sm:px-6">
      <h1 className="text-2xl font-bold text-gray-900 mb-8">My Profile</h1>

      <Card className="p-6 mb-6">
        <h2 className="text-lg font-semibold mb-4">Personal Information</h2>
        {message && <div className="mb-4 rounded-lg bg-green-50 p-3 text-sm text-green-700">{message}</div>}
        <form onSubmit={handleUpdate} className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <Input label="First Name" value={form.firstName} onChange={(e) => setForm({ ...form, firstName: e.target.value })} />
            <Input label="Last Name" value={form.lastName} onChange={(e) => setForm({ ...form, lastName: e.target.value })} />
          </div>
          <Input label="Email" type="email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
          <Input label="Phone" value={form.phoneNumber} onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })} />
          <Button type="submit" disabled={saving}>{saving ? 'Saving...' : 'Save Changes'}</Button>
        </form>
      </Card>

      <Card className="p-6">
        <h2 className="text-lg font-semibold mb-4">Change Password</h2>
        {pwMessage && <div className="mb-4 rounded-lg bg-green-50 p-3 text-sm text-green-700">{pwMessage}</div>}
        <form onSubmit={handleChangePassword} className="space-y-4">
          <Input label="Current Password" type="password" value={pwForm.oldPassword} onChange={(e) => setPwForm({ ...pwForm, oldPassword: e.target.value })} required />
          <Input label="New Password" type="password" value={pwForm.newPassword} onChange={(e) => setPwForm({ ...pwForm, newPassword: e.target.value })} required />
          <Button type="submit" disabled={changingPw}>{changingPw ? 'Changing...' : 'Change Password'}</Button>
        </form>
      </Card>
    </div>
  );
}
