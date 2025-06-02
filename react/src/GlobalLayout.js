import React from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { FaHome } from 'react-icons/fa';
import './GlobalLayout.css';

const GlobalLayout = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear token and redirect to login
    localStorage.removeItem('token');
    navigate('/');
  };

  const handleChangePassword = () => {
    navigate('/change-password');
  };

  const handleHome = () => {
    navigate('/dashboard');
  };

  return (
    <>
      <header className="global-header d-flex justify-content-between align-items-center p-3 bg-dark text-white">
        <div className="global-header-left" onClick={handleHome} style={{ cursor: 'pointer' }}>
          <FaHome size={24} />
        </div>
        <div className="global-header-right">
          <button className="btn-change-password" onClick={handleChangePassword}>
            <i className="bi bi-key"></i> Change Password
          </button>
          <button className="btn-logout" onClick={handleLogout}>
            <i className="bi bi-box-arrow-right"></i> Logout
          </button>

        </div>
      </header>

      <main className="app-content p-4">
        <Outlet /> {/* Render child routes here */}
      </main>
    </>
  );
};

export default GlobalLayout;
