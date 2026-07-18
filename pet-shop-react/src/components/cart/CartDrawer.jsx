import { formatCurrency } from '../../utils/formatters';

export default function CartDrawer({ open, items, totalAmount, totalItems, onClose, onChangeQuantity, onCheckout }) {
  if (!open) {
    return null;
  }

  return (
    <div className="drawer-backdrop" onClick={onClose}>
      <aside className="cart-drawer" onClick={(event) => event.stopPropagation()}>
        <div className="section-head">
          <div>
            <p className="eyebrow">Giỏ hàng</p>
            <h3>{totalItems} sản phẩm</h3>
          </div>
          <button className="ghost-button" onClick={onClose}>Đóng</button>
        </div>

        <div className="drawer-list">
          {items.length ? items.map((item) => (
            <div className="cart-row" key={item.id}>
              <img src={item.imageUrl} alt={item.name} />
              <div className="cart-copy">
                <strong>{item.name}</strong>
                <small>{formatCurrency(item.price)} x {item.quantity}</small>
                <div className="qty-controls">
                  <button onClick={() => onChangeQuantity(item, -1)}>-</button>
                  <span>{item.quantity}</span>
                  <button onClick={() => onChangeQuantity(item, 1)}>+</button>
                </div>
              </div>
            </div>
          )) : <div className="placeholder-card">Giỏ hàng đang trống.</div>}
        </div>

        <div className="drawer-footer">
          <div className="total-line">
            <span>Tổng cộng</span>
            <strong>{formatCurrency(totalAmount)}</strong>
          </div>
          <button className="solid-button" onClick={onCheckout}>Thanh toán</button>
        </div>
      </aside>
    </div>
  );
}
