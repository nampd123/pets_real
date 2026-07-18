import { useCatalog } from './hooks/useCatalog';
import { useAuth } from './hooks/useAuth';
import { useCart } from './hooks/useCart';
import { useCheckout } from './hooks/useCheckout';
import { useEffect, useState } from 'react';
import { Navigate, Route, Routes, useNavigate } from 'react-router-dom';
import { createPet, createPetImage, deletePet, fetchBreeds, fetchShops, fetchSpecies, updatePet, uploadPetImage } from './services/api';
import { getCategoryFromSpeciesName } from './utils/products';
import HomePage from './pages/HomePage';
import AdminTestPage from './pages/AdminTestPage';
import OrderHistoryPage from './pages/OrderHistoryPage';

function App() {
  const catalogState = useCatalog();
  const auth = useAuth();
  const cartState = useCart(auth.currentUser, catalogState.catalog);
  const checkout = useCheckout();
  const [adminState, setAdminState] = useState({
    isEditing: false,
    editingPet: null,
    saving: false,
    shops: [],
    speciesList: [],
    breedList: []
  });

  const isAdminUser = (auth.currentUser?.role || '')
    .toString()
    .toUpperCase()
    .includes('ADMIN');

  useEffect(() => {
    Promise.all([fetchShops(), fetchSpecies(), fetchBreeds()])
      .then(([shops, speciesList, breedList]) => {
        setAdminState((current) => ({ ...current, shops, speciesList, breedList }));
      })
      .catch(() => {
        setAdminState((current) => ({ ...current, shops: [], speciesList: [], breedList: [] }));
      });
  }, []);

  function openCreate(category = '') {
    setAdminState((current) => ({
      ...current,
      isEditing: true,
      editingPet: {
        shopId: '',
        speciesId: '',
        breedId: '',
        productCategory: category,
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
      }
    }));
  }

  async function openEdit(pet) {
    setAdminState((current) => ({
      ...current,
      isEditing: true,
      editingPet: {
        ...pet,
        productCategory: pet.productCategory || getCategoryFromSpeciesName(pet?.species?.name)
      }
    }));
  }

  function closeEdit() {
    setAdminState((current) => ({ ...current, isEditing: false, editingPet: null }));
  }

  function setEditingPet(update) {
    setAdminState((current) => ({
      ...current,
      editingPet: typeof update === 'function' ? update(current.editingPet) : update
    }));
  }

  async function savePet(event) {
    event.preventDefault();
    const editingPet = adminState.editingPet;
    if (!editingPet) {
      return;
    }

if (!editingPet.name?.trim() || !editingPet.shopId || !editingPet.productCategory || !editingPet.price) {
        window.alert('Vui lòng nhập tên, shop, danh mục và giá trước khi lưu.');
      return;
    }

    setAdminState((current) => ({ ...current, saving: true }));

    try {
      const payload = {
        shopId: editingPet.shopId,
        speciesId: editingPet.speciesId,
        breedId: editingPet.breedId || null,
        name: editingPet.name,
        gender: editingPet.gender || null,
        ageMonths: editingPet.ageMonths === '' ? null : Number(editingPet.ageMonths),
        color: editingPet.color || null,
        weightKg: editingPet.weightKg === '' ? null : Number(editingPet.weightKg),
        description: editingPet.description || null,
        healthStatus: editingPet.healthStatus || null,
        vaccinationStatus: editingPet.vaccinationStatus || null,
        quantity: Number.isFinite(Number(editingPet.quantity)) ? Number(editingPet.quantity) : 1,
        price: Number(editingPet.price),
        status: (Number.isFinite(Number(editingPet.quantity)) && Number(editingPet.quantity) > 0) ? 'AVAILABLE' : (editingPet.status || 'OUT_OF_STOCK')
      };

      const petResponse = editingPet.id
        ? await updatePet(editingPet.id, payload)
        : await createPet(payload);

      if (editingPet.imageFile) {
        await uploadPetImage(petResponse.id, editingPet.imageFile);
      } else if (!editingPet.id && editingPet.imageUrl?.trim()) {
        await createPetImage({ petId: petResponse.id, imageUrl: editingPet.imageUrl.trim(), thumbnail: true });
      }

      catalogState.reloadCatalog();
      closeEdit();
    } finally {
      setAdminState((current) => ({ ...current, saving: false }));
    }
  }

  async function removePet(pet) {
    const confirmed = window.confirm(`Xóa sản phẩm "${pet.name}"?`);
    if (!confirmed) {
      return;
    }

    await deletePet(pet.id);
    catalogState.reloadCatalog();
  }

  const navigate = useNavigate();

  return (
    <Routes>
      <Route
        path="/"
        element={
          <HomePage
            catalog={catalogState.catalog}
            loadingCatalog={catalogState.loadingCatalog}
            catalogError={catalogState.catalogError}
            filter={catalogState.filter}
            setFilter={catalogState.setFilter}
            auth={auth}
            cartState={cartState}
            onAddToCart={cartState.addToCart}
            onCheckoutSubmit={(event) => checkout.handleCheckout({
              event,
              currentUser: auth.currentUser,
              cartItems: cartState.cartItems,
              totalAmount: cartState.totalAmount,
              checkoutForm: cartState.checkoutForm,
              setCart: cartState.setCart,
              setShowCheckout: cartState.setShowCheckout,
              setShowCart: cartState.setShowCart,
              setCheckoutForm: cartState.setCheckoutForm,
              setCheckoutBusy: cartState.setCheckoutBusy,
              setCheckoutError: cartState.setCheckoutError
            })}
            onOpenCart={() => cartState.setShowCart(true)}
            onLogin={() => auth.setAuthMode('login')}
            onRegister={() => auth.setAuthMode('register')}
            onLogout={auth.handleLogout}
            isAdminUser={isAdminUser}
          />
        }
      />
      <Route
        path="/admin"
        element={
          isAdminUser ? (
            <AdminTestPage
              currentUser={auth.currentUser}
              adminState={{
                ...adminState,
                pets: catalogState.catalog,
                openCreate,
                openEdit,
                closeEdit,
                setEditingPet,
                savePet,
                deletePet: removePet
              }}
              onBack={() => navigate('/')}
            />
          ) : (
            <Navigate to="/" replace />
          )
        }
      />
      <Route
        path="/orders"
        element={<OrderHistoryPage currentUser={auth.currentUser} onBack={() => navigate('/')} />}
      />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
