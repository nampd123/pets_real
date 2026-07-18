export default function CheckoutModal({ open, busy, error, form, onClose, onChange, onSubmit, totalAmount }) {
  if (!open) {
    return null;
  }

  return (
    <div className="drawer-backdrop" onClick={onClose}>
      <aside className="checkout-modal" onClick={(event) => event.stopPropagation()}>
        <div className="section-head">
          <div>
            <p className="eyebrow">Thanh toán</p>
            <h3>Thông tin giao hàng</h3>
          </div>
          <button className="ghost-button" onClick={onClose}>Đóng</button>
        </div>

        {error ? <div className="error-card">{error}</div> : null}

        <form className="form-card" onSubmit={onSubmit}>
          <label>Họ và tên<input value={form.name} onChange={(event) => onChange((current) => ({ ...current, name: event.target.value }))} /></label>
          <label>Số điện thoại<input value={form.phone} onChange={(event) => onChange((current) => ({ ...current, phone: event.target.value }))} /></label>
          <label>Địa chỉ<textarea rows="3" value={form.address} onChange={(event) => onChange((current) => ({ ...current, address: event.target.value }))} /></label>
          <button className="solid-button" type="submit" disabled={busy}>
            {busy ? 'Đang xử lý...' : `Xác nhận đơn ${totalAmount}`}
          </button>
        </form>
      </aside>
    </div>
  );
}
