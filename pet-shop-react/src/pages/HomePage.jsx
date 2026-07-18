import Header from '../components/layout/Header';
import Hero from '../components/layout/Hero';
import ProductFilters from '../components/catalog/ProductFilters';
import ProductGrid from '../components/catalog/ProductGrid';
import AuthPanel from '../components/auth/AuthPanel';
import CartDrawer from '../components/cart/CartDrawer';
import CheckoutModal from '../components/cart/CheckoutModal';
import { classifyProduct } from '../utils/products';

function filterCatalog(catalog, filter) {
  if (filter === 'all') {
    return catalog;
  }

  return catalog.filter((item) => item.status !== 'INACTIVE' && classifyProduct(item) === filter);
}

export default function HomePage({
  catalog,
  loadingCatalog,
  catalogError,
  filter,
  setFilter,
  auth,
  cartState,
  onAddToCart,
  onCheckoutSubmit,
  onOpenCart,
  onLogin,
  onRegister,
  onLogout,
  isAdminUser
}) {
  const visibleCatalog = filterCatalog(catalog, filter);

  return (
    <div className="app-shell">
      <Header
        currentUser={auth.currentUser}
        totalItems={cartState.totalItems}
        onOpenCart={onOpenCart}
        onLogin={onLogin}
        onRegister={onRegister}
        onLogout={onLogout}
        isAdminUser={isAdminUser}
      />

      <Hero productCount={catalog.length} />

      <main className="content-grid">
        <section className="catalog-panel">
          <div className="section-head">
            <div>
              <p className="eyebrow">Danh mục</p>
              <h3>Sản phẩm từ API</h3>
            </div>
            <ProductFilters filter={filter} onChange={setFilter} />
          </div>

          <ProductGrid
            products={visibleCatalog}
            loading={loadingCatalog}
            error={catalogError}
            onAddToCart={onAddToCart}
          />
        </section>

        <AuthPanel
          authMode={auth.authMode}
          setAuthMode={auth.setAuthMode}
          authNotice={auth.authNotice}
          authError={auth.authError}
          loginForm={auth.loginForm}
          setLoginForm={auth.setLoginForm}
          registerForm={auth.registerForm}
          setRegisterForm={auth.setRegisterForm}
          onLogin={auth.handleLogin}
          onRegister={auth.handleRegister}
        />
      </main>

      <CartDrawer
        open={cartState.showCart}
        items={cartState.cartItems}
        totalAmount={cartState.totalAmount}
        totalItems={cartState.totalItems}
        onClose={cartState.closeCart}
        onChangeQuantity={cartState.changeQuantity}
        onCheckout={cartState.proceedToCheckout}
      />

      <CheckoutModal
        open={cartState.showCheckout}
        busy={cartState.checkoutBusy}
        error={cartState.checkoutError}
        form={cartState.checkoutForm}
        onClose={cartState.closeCheckout}
        onChange={cartState.setCheckoutForm}
        onSubmit={onCheckoutSubmit}
        totalAmount={cartState.totalAmount}
      />

    </div>
  );
}
