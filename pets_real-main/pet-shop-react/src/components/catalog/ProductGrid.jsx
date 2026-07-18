import { labelCategory } from '../../utils/products';
import { formatCurrency } from '../../utils/formatters';

export default function ProductGrid({ products, loading, error, onAddToCart }) {
  if (loading) {
    return <div className="placeholder-card">Đang tải catalog...</div>;
  }

  if (error) {
    return <div className="error-card">{error}</div>;
  }

  return (
    <div className="product-grid">
      {products.map((product) => (
        <article className="product-card" key={product.id}>
          <img src={product.imageUrl} alt={product.name} />
          <div className="product-meta">
            <span>{labelCategory(product)}</span>
            <small>{product.shop?.name || 'Shop đối tác'}</small>
          </div>
          <h4>{product.name}</h4>
          <p>{formatCurrency(product.price)}</p>
          {(() => {
            const stock = product.quantity ?? 0;
            const outOfStock = stock <= 0;
            return (
              <>
                <p style={{ margin: '0.25rem 0 0.75rem', color: outOfStock ? '#b00020' : '#5f6b78' }}>
                  {outOfStock ? 'Hết hàng' : `Tồn kho: ${stock}`}
                </p>
                <button
                  className="solid-button"
                  onClick={() => onAddToCart(product)}
                  disabled={outOfStock || product.status !== 'AVAILABLE'}
                >
                  {outOfStock ? 'Hết hàng' : 'Thêm vào giỏ'}
                </button>
              </>
            );
          })()}
        </article>
      ))}
    </div>
  );
}
