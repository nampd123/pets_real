export default function Hero({ productCount }) {
  return (
    <section className="hero-panel">
      <div>
        <p className="eyebrow">React + Spring Boot</p>
        <h2>Mua thú cưng, phụ kiện và thức ăn với một luồng checkout thật.</h2>
        <p>FE React lấy dữ liệu từ BE pet-ecommerce-system, đồng bộ sản phẩm, giỏ hàng, đơn hàng và thanh toán.</p>
      </div>
      <div className="hero-stat">
        <span>{productCount || '--'}</span>
        <small>sản phẩm sẵn sàng</small>
      </div>
    </section>
  );
}
