import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import GlobalLayout from './GlobalLayout';
import DashboardPage from './Dashboard/DashboardPage.js';
import CartPage from './Dashboard/CartPage.js';
import OrdersPage from './Dashboard/OrdersPage.js';
import ChangePasswordPage from './ChangePasswordPage.js';
import Login from './login.js';
import ProductCrudPage from './Admin/ProductCrudPage.js'; 

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/app" element={<GlobalLayout />}>
          <Route index element={<DashboardPage />} />
          <Route path="dashboard" element={<DashboardPage />} />
          <Route path="change-password" element={<ChangePasswordPage />} />
          <Route path="products" element={<ProductCrudPage />} />
          <Route path="cart" element={<CartPage />} />
          <Route path="orders" element={<OrdersPage />} />
        </Route>

        <Route path="/" element={<Login />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
