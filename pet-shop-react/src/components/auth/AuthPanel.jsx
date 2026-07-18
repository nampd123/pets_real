export default function AuthPanel({
  authMode,
  setAuthMode,
  authNotice,
  authError,
  loginForm,
  setLoginForm,
  registerForm,
  setRegisterForm,
  onLogin,
  onRegister
}) {
  return (
    <aside className="auth-panel">
      <div className="auth-tabs">
        <button className={authMode === 'login' ? 'tab active' : 'tab'} onClick={() => setAuthMode('login')}>Đăng nhập</button>
        <button className={authMode === 'register' ? 'tab active' : 'tab'} onClick={() => setAuthMode('register')}>Đăng ký</button>
      </div>

      {authNotice ? <div className="notice-card">{authNotice}</div> : null}
      {authError ? <div className="error-card">{authError}</div> : null}

      {authMode === 'login' ? (
        <form className="form-card" onSubmit={onLogin}>
          <label>
            Email
            <input
              type="email"
              value={loginForm.email}
              onChange={(event) => setLoginForm((current) => ({ ...current, email: event.target.value }))}
            />
          </label>
          <label>
            Mật khẩu
            <input
              type="password"
              value={loginForm.password}
              onChange={(event) => setLoginForm((current) => ({ ...current, password: event.target.value }))}
            />
          </label>
          <button className="solid-button" type="submit">Đăng nhập</button>
        </form>
      ) : (
        <form className="form-card" onSubmit={onRegister}>
          <label>
            Họ tên
            <input
              value={registerForm.fullName}
              onChange={(event) => setRegisterForm((current) => ({ ...current, fullName: event.target.value }))}
            />
          </label>
          <label>
            Email
            <input
              type="email"
              value={registerForm.email}
              onChange={(event) => setRegisterForm((current) => ({ ...current, email: event.target.value }))}
            />
          </label>
          <label>
            Số điện thoại
            <input
              value={registerForm.phone}
              onChange={(event) => setRegisterForm((current) => ({ ...current, phone: event.target.value }))}
            />
          </label>
          <label>
            Mật khẩu
            <input
              type="password"
              value={registerForm.password}
              onChange={(event) => setRegisterForm((current) => ({ ...current, password: event.target.value }))}
            />
          </label>
          <button className="solid-button" type="submit">Tạo tài khoản</button>
        </form>
      )}
    </aside>
  );
}
