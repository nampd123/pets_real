import { Link } from 'react-router-dom';

export default function Header({ currentUser, totalItems, onOpenCart, onLogin, onRegister, onLogout, isAdminUser }) {
  return (
    <header className="topbar">
      <div>
        <p className="eyebrow">PetShop React</p>
        <h1>Chợ thú cưng hiện đại</h1>
      </div>
      <div className="topbar-actions">
        {currentUser ? (
          <>
            <div className="user-pill">
              <span>{currentUser.fullName}</span>
              <small>{currentUser.email}</small>
            </div>
            <Link className="ghost-button" to="/orders">Đơn hàng của tôi</Link>
            {isAdminUser ? (
              <Link className="ghost-button" to="/admin">Quản lý sản phẩm</Link>
            ) : null}
            <button className="ghost-button" onClick={onLogout}>Đăng xuất</button>
          </>
        ) : (
          <>
            <button className="ghost-button" onClick={onLogin}>Đăng nhập</button>
            <button className="solid-button" onClick={onRegister}>Đăng ký</button>
          </>
        )}
        <button className="cart-button" onClick={onOpenCart}>
          Giỏ hàng <strong>{totalItems}</strong>
        </button>
      </div>
    </header>
  );
}
