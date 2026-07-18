import { useMemo } from 'react';

const emptyPet = {
  shopId: '',
  speciesId: '',
  breedId: '',
  productCategory: '',
  name: '',
  imageFile: null,
  gender: '',
  ageMonths: '',
  color: '',
  weightKg: '',
  description: '',
  healthStatus: '',
  vaccinationStatus: '',
  quantity: 1,
  price: '',
  status: 'AVAILABLE'
};

export default function PetEditModal({ open, pet, onClose, onChange, onSubmit, busy, shops, speciesList, breedList }) {
  const form = useMemo(() => ({ ...emptyPet, ...(pet || {}) }), [pet]);
  const isCreateMode = !pet?.id;

  if (!open) {
    return null;
  }

  return (
    <div className="drawer-backdrop" onClick={onClose}>
      <aside className="checkout-modal" onClick={(event) => event.stopPropagation()}>
        <div className="section-head">
          <div>
            <p className="eyebrow">Admin</p>
            <h3>{isCreateMode ? 'Thêm sản phẩm' : 'Chỉnh sửa sản phẩm'}</h3>
          </div>
          <button className="ghost-button" onClick={onClose}>Đóng</button>
        </div>

        <form className="form-card" onSubmit={onSubmit}>
          <label>
            Tên
            <input value={form.name || ''} onChange={(event) => onChange((current) => ({ ...current, name: event.target.value }))} />
          </label>
          <label>
            Shop
            <select value={form.shopId || ''} onChange={(event) => onChange((current) => ({ ...current, shopId: event.target.value }))}>
              <option value="">Chọn shop</option>
              {shops.map((shop) => <option key={shop.id} value={shop.id}>{shop.name}</option>)}
            </select>
          </label>
          <label>
            Danh mục sản phẩm
            <select value={form.productCategory || ''} onChange={(event) => onChange((current) => ({ ...current, productCategory: event.target.value, speciesId: '' }))}>
              <option value="">Chọn danh mục</option>
              <option value="thucung">Thú cưng</option>
              <option value="thucan">Thức ăn</option>
              <option value="phukien">Phụ kiện</option>
            </select>
          </label>
          <label>
            Nhóm / Giống
            <select value={form.speciesId || ''} onChange={(event) => onChange((current) => ({ ...current, speciesId: event.target.value }))}>
              <option value="">Chọn nhóm</option>
              {speciesList
                .filter((species) => {
                  const category = species.name.toLowerCase();
                  if (form.productCategory === 'thucan') {
                    return category.includes('thức ăn') || category.includes('food');
                  }
                  if (form.productCategory === 'phukien') {
                    return category.includes('phụ kiện') || category.includes('accessory');
                  }
                  return !category.includes('thức ăn') && !category.includes('food') && !category.includes('phụ kiện') && !category.includes('accessory');
                })
                .map((species) => <option key={species.id} value={species.id}>{species.name}</option>)}
            </select>
          </label>
          <label>
            Ảnh sản phẩm
            <input
              type="file"
              accept="image/jpeg,image/png"
              onChange={(event) => onChange((current) => ({
                ...current,
                imageFile: event.target.files?.[0] || null
              }))}
            />
          </label>
          {form.productCategory === 'thucung' && (
            <>
              <label>
                Giới tính
                <input value={form.gender || ''} onChange={(event) => onChange((current) => ({ ...current, gender: event.target.value }))} />
              </label>
              <label>
                Tuổi (tháng)
                <input type="number" value={form.ageMonths || ''} onChange={(event) => onChange((current) => ({ ...current, ageMonths: event.target.value }))} />
              </label>
              <label>
                Màu sắc
                <input value={form.color || ''} onChange={(event) => onChange((current) => ({ ...current, color: event.target.value }))} />
              </label>
              <label>
                Cân nặng (kg)
                <input type="number" step="0.1" value={form.weightKg || ''} onChange={(event) => onChange((current) => ({ ...current, weightKg: event.target.value }))} />
              </label>
            </>
          )}
          {form.productCategory === 'thucan' && (
            <label>
              Cân nặng (kg)
              <input type="number" step="0.1" value={form.weightKg || ''} onChange={(event) => onChange((current) => ({ ...current, weightKg: event.target.value }))} />
            </label>
          )}
          {form.productCategory === 'phukien' && (
            <label>
              Màu sắc
              <input value={form.color || ''} onChange={(event) => onChange((current) => ({ ...current, color: event.target.value }))} />
            </label>
          )}
          <label>
            Mô tả
            <textarea value={form.description || ''} onChange={(event) => onChange((current) => ({ ...current, description: event.target.value }))} />
          </label>
          {form.productCategory === 'thucung' && (
            <>
              <label>
                Tình trạng sức khỏe
                <input value={form.healthStatus || ''} onChange={(event) => onChange((current) => ({ ...current, healthStatus: event.target.value }))} />
              </label>
              <label>
                Tình trạng tiêm phòng
                <input value={form.vaccinationStatus || ''} onChange={(event) => onChange((current) => ({ ...current, vaccinationStatus: event.target.value }))} />
              </label>
            </>
          )}
          <label>
            Giá
            <input type="number" step="1000" value={form.price || ''} onChange={(event) => onChange((current) => ({ ...current, price: event.target.value }))} />
          </label>
          <label>
            Tồn kho
            <input type="number" min="0" step="1" value={form.quantity ?? 1} onChange={(event) => onChange((current) => ({ ...current, quantity: Number(event.target.value) }))} />
          </label>
          <label>
            Trạng thái
            <input value={form.status || ''} onChange={(event) => onChange((current) => ({ ...current, status: event.target.value }))} />
          </label>
          <button className="solid-button" type="submit" disabled={busy}>{isCreateMode ? 'Thêm sản phẩm' : 'Lưu thay đổi'}</button>
        </form>
      </aside>
    </div>
  );
}
