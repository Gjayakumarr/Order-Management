import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { apiPOSTRequest } from '../networking';

const CartPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { cart, products } = location.state || { cart: {}, products: [] };

  const cartItems = Object.keys(cart).map((id) => {
    const product = products.find((p) => p.id === id);
    return {
      ...product,
      quantity: cart[id],
      totalPrice: product.unitPrice * cart[id],
    };
  });

  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    customerName: '',
    email: '',
    phone: '',
    shippingAddress: '',
    paymentMethod: 'COD', // default
  });
  const [loading, setLoading] = useState(false);

  const grandTotal = cartItems.reduce((sum, item) => sum + item.totalPrice, 0);

  const handleInputChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const submitOrder = async (e) => {
    e.preventDefault();
    setLoading(true);

    const orderItems = cartItems.map(({ id, name, quantity, unitPrice, totalPrice }) => ({
      productId: id,
      productName: name,
      quantity,
      unitPrice,
      totalPrice,
    }));

    const orderPayload = {
      ...formData,
      orderDate: new Date().toISOString(),
      grandTotal,
      status: 'Pending',
      deliveryDate: null,
      isCancelled: false,
      cancelledOn: null,
      orderItems,
    };

    try {
      const token = localStorage.getItem('token');
      await apiPOSTRequest('/order-management/create', orderPayload, {
        Authorization: `Bearer ${token}`,
      });
      alert('✅ Order placed successfully!');
      navigate('/app/orders');
    } catch (error) {
      console.error('❌ Failed to place order:', error);
      alert('❌ Failed to place order.');
      setLoading(false);
    }
  };

  if (cartItems.length === 0) {
    return (
      <div className="container mt-5 text-center">
        <h4>Your cart is empty</h4>
        <button className="btn btn-primary mt-3" onClick={() => navigate('/app/dashboard')}>
          Back to Dashboard
        </button>
      </div>
    );
  }

  return (
    <div className="container mt-5">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="text-primary fw-bold">🛒 Your Cart</h2>
        <button className="btn btn-outline-secondary" onClick={() => navigate('/app/dashboard')}>
          ← Back to Dashboard
        </button>
      </div>

      <div className="table-responsive">
        <table className="table table-striped table-hover border rounded shadow-sm">
          <thead className="table-primary">
            <tr>
              <th>Product</th>
              <th>Price (₹)</th>
              <th>Quantity</th>
              <th>Total (₹)</th>
            </tr>
          </thead>
          <tbody>
            {cartItems.map((item) => (
              <tr key={item.id}>
                <td className="fw-semibold">{item.name}</td>
                <td>{item.unitPrice}</td>
                <td>{item.quantity}</td>
                <td className="fw-bold text-success">{item.totalPrice}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <h4 className="mt-4 text-end">Grand Total: ₹{grandTotal.toFixed(2)}</h4>

      {!showForm ? (
        <div className="text-end mt-4">
          <button
            className="btn btn-success btn-lg fw-bold shadow px-5"
            onClick={() => setShowForm(true)}
          >
            ✅ Place Order
          </button>
        </div>
      ) : (
        <form className="mt-4" onSubmit={submitOrder}>
          <h3 className="mb-3">Enter Your Details</h3>
          <div className="mb-3">
            <label htmlFor="customerName" className="form-label">Full Name</label>
            <input
              type="text"
              className="form-control"
              id="customerName"
              name="customerName"
              value={formData.customerName}
              onChange={handleInputChange}
              required
              disabled={loading}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="email" className="form-label">Email address</label>
            <input
              type="email"
              className="form-control"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              required
              disabled={loading}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="phone" className="form-label">Phone Number</label>
            <input
              type="tel"
              className="form-control"
              id="phone"
              name="phone"
              value={formData.phone}
              onChange={handleInputChange}
              required
              disabled={loading}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="shippingAddress" className="form-label">Shipping Address</label>
            <textarea
              className="form-control"
              id="shippingAddress"
              name="shippingAddress"
              rows="3"
              value={formData.shippingAddress}
              onChange={handleInputChange}
              required
              disabled={loading}
            ></textarea>
          </div>

          <div className="mb-3">
            <label htmlFor="paymentMethod" className="form-label">Payment Method</label>
            <select
              className="form-select"
              id="paymentMethod"
              name="paymentMethod"
              value={formData.paymentMethod}
              onChange={handleInputChange}
              disabled={loading}
            >
              <option value="COD">Cash on Delivery</option>
              <option value="Card">Card Payment</option>
              <option value="UPI">UPI</option>
            </select>
          </div>

          <div className="d-flex justify-content-between">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={() => setShowForm(false)}
              disabled={loading}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary"
              disabled={loading}
            >
              {loading ? 'Placing Order...' : 'Confirm & Place Order'}
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default CartPage;
