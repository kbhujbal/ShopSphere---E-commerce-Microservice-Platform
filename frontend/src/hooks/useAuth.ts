import { useAppSelector, useAppDispatch } from '../store/hooks';
import { logout as logoutAction } from '../features/auth/authSlice';

export function useAuth() {
  const dispatch = useAppDispatch();
  const { user, token, isAuthenticated } = useAppSelector((state) => state.auth);

  const isAdmin = user?.roles?.includes('ROLE_ADMIN') ?? false;

  const handleLogout = () => {
    dispatch(logoutAction());
  };

  return {
    user,
    token,
    isAuthenticated,
    isAdmin,
    logout: handleLogout,
  };
}
