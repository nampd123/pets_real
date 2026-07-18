import PetAdminPanel from '../components/admin/PetAdminPanel';
import PetEditModal from '../components/admin/PetEditModal';

export default function AdminTestPage({ currentUser, adminState, onBack }) {
  return (
    <div className="app-shell">
      <header className="topbar">
        <div>
          <p className="eyebrow">Admin</p>
          <h1>Quản lý sản phẩm</h1>
        </div>
        <button className="ghost-button" onClick={onBack}>Về trang chủ</button>
      </header>

      <main className="content-grid admin-page">
        <PetAdminPanel
          visible={true}
          pets={adminState.pets || []}
          onEdit={adminState.openEdit}
          onDelete={adminState.deletePet}
          onCreate={adminState.openCreate}
        />

        <PetEditModal
          open={adminState.isEditing}
          pet={adminState.editingPet}
          onClose={adminState.closeEdit}
          onChange={adminState.setEditingPet}
          onSubmit={adminState.savePet}
          busy={adminState.saving}
          shops={adminState.shops}
          speciesList={adminState.speciesList}
          breedList={adminState.breedList}
        />
      </main>
    </div>
  );
}
