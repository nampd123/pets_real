import { useEffect, useMemo, useState } from 'react';
import { deleteCartItem, getCartItems, getOrCreateCart, upsertCartItem } from '../services/api';

const emptyAddress = { name: '', phone: '', address: '' };

export function useCart(currentUser, catalog) {
  const [cart, setCart] = useState([]);
  const [cartId, setCartId] = useState('');
  const [showCart, setShowCart] = useState(false);
  const [showCheckout, setShowCheckout] = useState(false);
  const [checkoutForm, setCheckoutForm] = useState(emptyAddress);
  const [checkoutError, setCheckoutError] = useState('');
  const [checkoutBusy, setCheckoutBusy] = useState(false);

  useEffect(() => {
    if (!currentUser?.id) {
      setCart([]);
      setCartId('');
      return;
    }

    let active = true;
    getOrCreateCart(currentUser.id)
      .then((cartResponse) => {
        if (!active) {
          return;
        }
        setCartId(cartResponse.id);
        return getCartItems(cartResponse.id);
      })
      .then((items) => {
        if (active && Array.isArray(items)) {
          setCart(items);
        }
      })
      .catch((error) => {
        if (active) {
          setCheckoutError(error.message);
        }
      });

    return () => {
      active = false;
    };
  }, [currentUser?.id]);

  const cartItems = useMemo(() => {
    return cart.map((item) => {
      const product = catalog.find((entry) => entry.id === item.petId);
      return {
        ...item,
        product,
        name: product?.name || 'Sản phẩm',
        imageUrl: product?.imageUrl || '',
        shopId: product?.shopId || ''
      };
    });
  }, [cart, catalog]);

  const totalItems = cartItems.reduce((sum, item) => sum + (item.quantity || 0), 0);
  const totalAmount = cartItems.reduce((sum, item) => sum + Number(item.price || 0) * Number(item.quantity || 0), 0);

  async function addToCart(product) {
    if (!cartId) {
      return;
    }

    const existing = cart.find((item) => item.petId === product.id);
    const nextQuantity = (existing?.quantity || 0) + 1;

    const updated = await upsertCartItem({
      cartId,
      petId: product.id,
      quantity: nextQuantity,
      price: product.price
    });

    setCart((current) => {
      const withoutCurrent = current.filter((item) => item.id !== updated.id);
      return [...withoutCurrent, updated].sort((a, b) => a.createdAt.localeCompare(b.createdAt));
    });
    setShowCart(true);
  }

  async function changeQuantity(item, delta) {
    const nextQuantity = Number(item.quantity || 0) + delta;
    if (nextQuantity <= 0) {
      await deleteCartItem(item.id);
      setCart((current) => current.filter((entry) => entry.id !== item.id));
      return;
    }

    const updated = await upsertCartItem({
      cartId,
      petId: item.petId,
      quantity: nextQuantity,
      price: item.price
    });

    setCart((current) => current.map((entry) => (entry.id === updated.id ? updated : entry)));
  }

  function proceedToCheckout() {
    if (!currentUser?.id || !cartItems.length) {
      return false;
    }

    setShowCheckout(true);
    setCheckoutError('');
    return true;
  }

  function closeCart() {
    setShowCart(false);
  }

  function closeCheckout() {
    setShowCheckout(false);
  }

  return {
    cart,
    setCart,
    cartId,
    cartItems,
    totalItems,
    totalAmount,
    showCart,
    setShowCart,
    closeCart,
    showCheckout,
    setShowCheckout,
    closeCheckout,
    checkoutForm,
    setCheckoutForm,
    checkoutError,
    setCheckoutError,
    checkoutBusy,
    setCheckoutBusy,
    addToCart,
    changeQuantity,
    proceedToCheckout,
    emptyAddress
  };
}
