import { formatCurrency } from '../../utils/formatters';
import { labelCategory } from '../../utils/products';

export default function PetAdminPanel({ pets, onEdit, onDelete, onCreate, visible }) {
  if (!visible) {
    return null;
  }

  return (
    <section className="catalog-panel admin-panel" style={{ marginTop: '20px' }}>
      <div className="section-head">
        <div>
          <p className="eyebrow">Admin</p>
          <h3>Quản lý sản phẩm</h3>
        </div>
        <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap', alignItems: 'center' }}>
          <button className="solid-button" onClick={() => onCreate('thucung')}>Thêm thú cưng</button>
          <button className="solid-button" onClick={() => onCreate('thucan')}>Thêm thức ăn</button>
          <button className="solid-button" onClick={() => onCreate('phukien')}>Thêm phụ kiện</button>
        </div>
      </div>

      <div className="product-grid admin-product-grid">
        {pets.length === 0 ? (
          <p>Chưa có sản phẩm nào để quản lý.</p>
        ) : (
          pets.map((pet) => (
            <article className="product-card product-card-admin" key={pet.id}>
              <img src={pet.imageUrl} alt={pet.name} />
              <div className="product-meta">
                <span>{labelCategory(pet)}</span>
                <small>{pet.status || 'AVAILABLE'}</small>
              </div>
              <h4>{pet.name}</h4>
              <p>{formatCurrency(pet.price)}</p>
              <div className="admin-card-actions">
                <button className="ghost-button" onClick={() => onEdit(pet)}>Sửa</button>
                <button className="ghost-button danger" onClick={() => onDelete(pet)}>Xóa</button>
              </div>
            </article>
          ))
        )}
      </div>
    </section>
  );
}
