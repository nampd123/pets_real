import { useEffect, useMemo, useState } from 'react';
import { fetchOrderItems, fetchOrders } from '../services/api';

function formatCurrency(value) {
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND'
  }).format(value || 0);
}

export default function OrderHistoryPage({ currentUser, onBack }) {
  const [orders, setOrders] = useState([]);
  const [orderItems, setOrderItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!currentUser) {
      setLoading(false);
      return;
    }

    setLoading(true);
    setError('');

    Promise.all([fetchOrders(), fetchOrderItems()])
      .then(([ordersResponse, itemsResponse]) => {
        setOrders(ordersResponse.filter((order) => order.customerId === currentUser.id));
        setOrderItems(itemsResponse);
      })
      .catch((err) => {
        setError(err.message || 'Không tải được đơn hàng');
      })
      .finally(() => {
        setLoading(false);
      });
  }, [currentUser]);

  const ordersGrouped = useMemo(() => {
    return orders.map((order) => ({
      ...order,
      items: orderItems.filter((item) => item.orderId === order.id)
    }));
  }, [orders, orderItems]);

  if (!currentUser) {
    return (
      <div className="app-shell">
        <div className="section-head">
          <h3>Đơn hàng</h3>
        </div>
        <p>Bạn cần đăng nhập để xem đơn hàng.</p>
      </div>
    );
  }

  return (
    <div className="app-shell">
      <div className="section-head">
        <div>
          <p className="eyebrow">Đơn hàng của tôi</p>
          <h3>Danh sách đơn hàng</h3>
        </div>
        <button className="ghost-button" onClick={onBack}>Quay lại</button>
      </div>

      {loading ? (
        <p>Đang tải đơn hàng...</p>
      ) : error ? (
        <p className="error-message">{error}</p>
      ) : ordersGrouped.length === 0 ? (
        <p>Chưa có đơn hàng nào.</p>
      ) : (
        <div className="order-history-list">
          {ordersGrouped.map((order) => (
            <div className="order-card" key={order.id}>
              <div className="order-card-header">
                <div>
                  <p className="eyebrow">Mã đơn</p>
                  <strong>{order.id}</strong>
                </div>
                <div>
                  <p className="eyebrow">Trạng thái</p>
                  <strong>{order.status}</strong>
                </div>
                <div>
                  <p className="eyebrow">Ngày tạo</p>
                  <strong>{new Date(order.createdAt).toLocaleString('vi-VN')}</strong>
                </div>
              </div>

              <div className="order-card-body">
                <p><strong>Người nhận:</strong> {order.receiverName}</p>
                <p><strong>Số điện thoại:</strong> {order.receiverPhone}</p>
                <p><strong>Địa chỉ:</strong> {order.receiverAddress}</p>
                {order.note ? <p><strong>Ghi chú:</strong> {order.note}</p> : null}
                <p><strong>Tổng đơn:</strong> {formatCurrency(order.totalAmount)}</p>
                <p><strong>Phí ship:</strong> {formatCurrency(order.shippingFee)}</p>
                <p><strong>Thanh toán:</strong> {formatCurrency(order.finalAmount)}</p>
              </div>

              <div className="order-card-items">
                <h4>Chi tiết sản phẩm</h4>
                <table>
                  <thead>
                    <tr>
                      <th>Sản phẩm</th>
                      <th>Số lượng</th>
                      <th>Giá</th>
                    </tr>
                  </thead>
                  <tbody>
                    {order.items.map((item) => (
                      <tr key={item.id}>
                        <td>{item.petName}</td>
                        <td>{item.quantity}</td>
                        <td>{formatCurrency(item.price)}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
