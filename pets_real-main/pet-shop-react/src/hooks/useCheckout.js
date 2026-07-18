import { createOrder, createOrderItem, createPayment, deleteCartItem } from '../services/api';

export function useCheckout() {
  async function handleCheckout({ event, currentUser, cartItems, totalAmount, checkoutForm, setCart, setShowCheckout, setShowCart, setCheckoutForm, setCheckoutBusy, setCheckoutError }) {
    event.preventDefault();
    setCheckoutBusy(true);
    setCheckoutError('');

    try {
      if (!checkoutForm.name || !checkoutForm.phone || !checkoutForm.address) {
        throw new Error('Vui lòng điền đầy đủ thông tin giao hàng');
      }

      const firstShopId = cartItems.find((item) => item.shopId)?.shopId;
      if (!firstShopId) {
        throw new Error('Không xác định được shop cho đơn hàng');
      }

      const order = await createOrder({
        customerId: currentUser.id,
        shopId: firstShopId,
        totalAmount,
        shippingFee: 0,
        finalAmount: totalAmount,
        receiverName: checkoutForm.name,
        receiverPhone: checkoutForm.phone,
        receiverAddress: checkoutForm.address,
        note: 'Đặt hàng từ storefront React',
        status: 'PENDING'
      });

      await Promise.all(
        cartItems.map((item) =>
          createOrderItem({
            orderId: order.id,
            petId: item.petId,
            petName: item.name,
            quantity: item.quantity,
            price: item.price
          })
        )
      );

      await createPayment({
        orderId: order.id,
        paymentMethod: 'COD',
        amount: totalAmount,
        status: 'SUCCESS',
        transactionCode: `COD-${order.id}`,
        paidAt: new Date().toISOString()
      });

      await Promise.all(cartItems.map((item) => deleteCartItem(item.id)));
      setCart([]);
      setShowCheckout(false);
      setShowCart(false);
      setCheckoutForm({ name: '', phone: '', address: '' });
      alert('Đặt hàng thành công');
    } catch (error) {
      setCheckoutError(error.message);
    } finally {
      setCheckoutBusy(false);
    }
  }

  return { handleCheckout };
}
