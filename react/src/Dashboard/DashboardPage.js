import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { apiGetRequest } from '../networking';
import './DashboardPage.css';

const DashboardPage = () => {
  const [orders, setOrders] = useState({ placed: 0, cancelled: 0, pending: 0 });
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const token = localStorage.getItem('token');

        const orderData = await apiGetRequest('/order-management/dashboardSummary', {
          Authorization: `Bearer ${token}`,
        });
        setOrders(orderData.data);

        const productData = await apiGetRequest('/products/getAll', {
          Authorization: `Bearer ${token}`,
        });
        setProducts(productData.allProducts);
      } catch (error) {
        console.error('Failed to load dashboard data:', error);
      }
    };

    fetchDashboardData();
  }, []);

  const addToCart = (productId) => {
    setCart((prev) => ({
      ...prev,
      [productId]: (prev[productId] || 0) + 1,
    }));
  };

  const removeFromCart = (productId) => {
    setCart((prev) => {
      const updated = { ...prev };
      if (updated[productId] > 1) updated[productId]--;
      else delete updated[productId];
      return updated;
    });
  };

  const handleGoToCart = () => {
    const selectedProductDetails = products
      .filter((p) => cart[p.id])
      .map((p) => ({
        id: p.id,
        name: p.name,
        quantity: cart[p.id],
        unitPrice: p.unitPrice,
        totalPrice: p.unitPrice * cart[p.id],
      }));

    navigate('/app/cart', {
      state: {
        cart,
        products: selectedProductDetails,
      },
    });
  };

  return (
    <div className="dashboard-container">
      {/* Order Counts */}
      <section className="dashboard-counts container mt-4">
        <div className="d-flex justify-content-center gap-4 flex-wrap">
          <div className="card count-card text-white bg-primary">
            <div className="card-body text-center">
              <p className="display-5 fw-bold mb-2">{orders.placed}</p>
              <h6 className="card-title">Orders Placed</h6>
            </div>
          </div>
          <div className="card count-card text-white bg-danger">
            <div className="card-body text-center">
              <p className="display-5 fw-bold mb-2">{orders.cancelled}</p>
              <h6 className="card-title">Cancelled Orders</h6>
            </div>
          </div>
          <div className="card count-card text-dark bg-warning">
            <div className="card-body text-center">
              <p className="display-5 fw-bold mb-2">{orders.pending}</p>
              <h6 className="card-title">Pending Orders</h6>
            </div>
          </div>
        </div>
      </section>

      {/* Product List */}
      <section className="product-section mt-5 px-3">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h3 className="text-primary">Available Products</h3>
          <button
            className="btn btn-success"
            onClick={() => navigate('/app/products')}
          >
            ➕ Add Product
          </button>
        </div>

        <div className="row row-cols-1 row-cols-md-3 g-4 mt-2">
          {products.map((product) => (
            <div key={product.id} className="col">
              <div className="card h-100 product-card border-primary shadow-sm">
                <img
                  src={product.imageBase64}
                  className="img-thumbnail"
                  alt={product.name}
                  style={{ height: '200px', objectFit: 'cover' }}
                />
                <div className="card-body">
                  <h5 className="card-title text-dark fw-semibold">{product.name}</h5>
                  <p className="card-text">Price: ₹{product.unitPrice}</p>
                  <p className="card-text">Available: {product.stock}</p>
                  <div className="d-flex align-items-center gap-2">
                    <button className="btn btn-outline-secondary" onClick={() => removeFromCart(product.id)}>-</button>
                    <span className="fw-bold">{cart[product.id] || 0}</span>
                    <button className="btn btn-outline-primary" onClick={() => addToCart(product.id)}>+</button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* Go to Cart */}
      <div className="container mt-4 text-end">
        <button
          className="btn btn-lg btn-warning fw-bold px-4 shadow"
          onClick={handleGoToCart}
          disabled={Object.keys(cart).length === 0}
        >
          🛒 Go to Cart
        </button>
      </div>
    </div>
  );
};

export default DashboardPage;
