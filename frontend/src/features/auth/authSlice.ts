import { createSlice, type PayloadAction } from '@reduxjs/toolkit';
import type { AuthResponse } from './authTypes';
import { storage } from '../../utils/storage';
import { decodeJwt, isTokenExpired } from '../../utils/decodeJwt';

interface AuthUser {
  userId: string;
  username: string;
  email: string;
  roles: string[];
}

interface AuthState {
  user: AuthUser | null;
  token: string | null;
  isAuthenticated: boolean;
}

function loadInitialState(): AuthState {
  const token = storage.getToken();
  if (token && !isTokenExpired(token)) {
    const payload = decodeJwt(token);
    if (payload) {
      return {
        user: {
          userId: payload.sub,
          username: payload.username,
          email: '',
          roles: payload.roles,
        },
        token,
        isAuthenticated: true,
      };
    }
  }
  storage.removeToken();
  return { user: null, token: null, isAuthenticated: false };
}

const authSlice = createSlice({
  name: 'auth',
  initialState: loadInitialState(),
  reducers: {
    setCredentials: (state, action: PayloadAction<AuthResponse>) => {
      const { token, userId, username, email, roles } = action.payload;
      state.token = token;
      state.user = { userId, username, email, roles };
      state.isAuthenticated = true;
      storage.setToken(token);
    },
    logout: (state) => {
      state.token = null;
      state.user = null;
      state.isAuthenticated = false;
      storage.removeToken();
    },
  },
});

export const { setCredentials, logout } = authSlice.actions;
export default authSlice.reducer;
