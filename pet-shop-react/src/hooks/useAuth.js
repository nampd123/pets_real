import { useEffect, useState } from 'react';
import { login, register } from '../services/api';

const emptyLogin = { email: '', password: '' };
const emptyRegister = { fullName: '', email: '', phone: '', password: '' };

export function useAuth() {
  const [authMode, setAuthMode] = useState('login');
  const [loginForm, setLoginForm] = useState(emptyLogin);
  const [registerForm, setRegisterForm] = useState(emptyRegister);
  const [authError, setAuthError] = useState('');
  const [authNotice, setAuthNotice] = useState('');
  const [currentUser, setCurrentUser] = useState(() => JSON.parse(localStorage.getItem('petshop-user') || 'null'));
  const [authToken, setAuthToken] = useState(() => localStorage.getItem('petshop-token') || '');

  useEffect(() => {
    if (currentUser) {
      localStorage.setItem('petshop-user', JSON.stringify(currentUser));
    } else {
      localStorage.removeItem('petshop-user');
    }
  }, [currentUser]);

  useEffect(() => {
    if (authToken) {
      localStorage.setItem('petshop-token', authToken);
    } else {
      localStorage.removeItem('petshop-token');
    }
  }, [authToken]);

  async function handleLogin(event) {
    event.preventDefault();
    setAuthError('');
    setAuthNotice('');

    try {
      const response = await login(loginForm);
      const user = response.user || response;
      setCurrentUser(user);
      setAuthToken(response.token || '');
      setAuthNotice('Đăng nhập thành công');
      setAuthMode('login');
    } catch (error) {
      setAuthError(error.message);
    }
  }

  async function handleRegister(event) {
    event.preventDefault();
    setAuthError('');
    setAuthNotice('');

    try {
      const response = await register(registerForm);
      const user = response.user || response;
      setCurrentUser(user);
      setAuthToken(response.token || '');
      setAuthNotice('Tạo tài khoản thành công');
      setAuthMode('login');
    } catch (error) {
      setAuthError(error.message);
    }
  }

  function handleLogout() {
    setCurrentUser(null);
    setAuthToken('');
  }

  return {
    authMode,
    setAuthMode,
    loginForm,
    setLoginForm,
    registerForm,
    setRegisterForm,
    authError,
    authNotice,
    setAuthError,
    setAuthNotice,
    currentUser,
    authToken,
    handleLogin,
    handleRegister,
    handleLogout,
    emptyLogin,
    emptyRegister
  };
}
