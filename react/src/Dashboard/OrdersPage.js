import React, { useEffect, useState } from 'react';
import { apiGetRequest } from '../networking';
import 'bootstrap/dist/css/bootstrap.min.css';

const OrdersPage = () => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const token = localStorage.getItem('token');
        const data = await apiGetRequest('/order-management/getAllOrders', {
          Authorization: `Bearer ${token}`,
        });
        setOrders(data.details || []);
      } catch (err) {
        console.error('Failed to fetch orders:', err.message);
      }
    };

    fetchOrders();
  }, []);

  return (
    <div className="container my-5">
      <h2 className="text-primary mb-4">📦 Your Orders</h2>

      {orders.length === 0 ? (
        <div className="alert alert-info text-center">No orders found.</div>
      ) : (
        orders.map((order) => (
          <div className="card mb-4 shadow-sm border-primary" key={order.id}>
            <div className="card-header bg-primary text-white d-flex justify-content-between align-items-center">
              <strong>Order ID: {order.id}</strong>
              <span className={`badge bg-${order.status === 'Delivered' ? 'success' : order.status === 'Pending' ? 'warning text-dark' : 'secondary'}`}>
                {order.status}
              </span>
            </div>

            <div className="card-body">
              <div className="row mb-3">
                <div className="col-md-6">
                  <p><strong>Name:</strong> {order.customerName}</p>
                  <p><strong>Email:</strong> {order.email}</p>
                  <p><strong>Phone:</strong> {order.phone}</p>
                  <p><strong>Address:</strong> {order.shippingAddress}</p>
                </div>
                <div className="col-md-6">
                  <p><strong>Order Date:</strong> {order.orderDate}</p>
                  <p><strong>Delivery Date:</strong> {order.deliveryDate || '-'}</p>
                  <p><strong>Payment:</strong> {order.paymentMethod}</p>
                  <p>
                    <strong>Cancelled:</strong>{' '}
                    <span className={`badge bg-${order.isCancelled ? 'danger' : 'success'}`}>
                      {order.isCancelled ? `Yes (${order.cancelledOn})` : 'No'}
                    </span>
                  </p>
                  <p><strong>Grand Total:</strong> ₹{order.grandTotal.toFixed(2)}</p>
                </div>
              </div>

              <div className="table-responsive">
                <table className="table table-bordered table-striped">
                  <thead className="table-light">
                    <tr>
                      <th>Product</th>
                      <th>Qty</th>
                      <th>Unit Price (₹)</th>
                      <th>Total Price (₹)</th>
                    </tr>
                  </thead>
                  <tbody>
                    {order.orderItems && order.orderItems.length > 0 ? (
                      order.orderItems.map((item) => (
                        <tr key={item.id}>
                          <td>{item.productName}</td>
                          <td>{item.quantity}</td>
                          <td>{item.unitPrice}</td>
                          <td className="fw-bold text-success">{item.totalPrice}</td>
                        </tr>
                      ))
                    ) : (
                      <tr>
                        <td colSpan="4" className="text-center text-muted">No items found</td>
                      </tr>
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default OrdersPage;
