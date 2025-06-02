import React, { useState, useEffect } from 'react';
import { apiGetRequest, apiPOSTRequest, apiPUTRequest, apiDELETERequest } from '../networking';

const initialProduct = {
  name: '',
  description: '',
  category: '',
  unitPrice: '',
  stock: '',
  imageBase64: '',
};

const ProductCrudPage = () => {
  const [products, setProducts] = useState([]);
  const [formAdd, setFormAdd] = useState(initialProduct);
  const [formUpdate, setFormUpdate] = useState(initialProduct);
  const [isEditing, setIsEditing] = useState(false);
  const [error, setError] = useState('');
  const [successMsg, setSuccessMsg] = useState('');

  const fetchProducts = async () => {
    const token = localStorage.getItem('token');
    try {
      const data = await apiGetRequest('/products/getAll', {
        Authorization: `Bearer ${token}`,
      });
      setProducts(data.allProducts);
    } catch (err) {
      setError('Failed to load products');
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (isEditing) {
      setFormUpdate((prev) => ({ ...prev, [name]: value }));
    } else {
      setFormAdd((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleImageUpload = (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.onloadend = () => {
      if (isEditing) {
        setFormUpdate((prev) => ({ ...prev, imageBase64: reader.result }));
      } else {
        setFormAdd((prev) => ({ ...prev, imageBase64: reader.result }));
      }
    };
    if (file) reader.readAsDataURL(file);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccessMsg('');
    const token = localStorage.getItem('token');

    try {
      if (isEditing) {
        await apiPUTRequest(`/products/update/${formUpdate.id}`, formUpdate, {
          Authorization: `Bearer ${token}`,
        });
        setSuccessMsg('Product updated successfully');
      } else {
        await apiPOSTRequest('/products', formAdd, {
          Authorization: `Bearer ${token}`,
        });
        setSuccessMsg('Product created successfully');
      }
      fetchProducts();
      setFormAdd(initialProduct);
      setFormUpdate(initialProduct);
      setIsEditing(false);
    } catch (err) {
      setError('Failed to save product');
    }
  };

  const handleEdit = (product) => {
    setFormUpdate(product);
    setIsEditing(true);
  };

  const handleDelete = async (id) => {
    const token = localStorage.getItem('token');
    try {
      await apiDELETERequest(`/products/delete/${id}`, {
        Authorization: `Bearer ${token}`,
      });
      fetchProducts();
    } catch (err) {
      setError('Failed to delete product');
    }
  };

  const currentForm = isEditing ? formUpdate : formAdd;

  return (
    <div className="container mt-4">
      <div className="card shadow-sm mb-4">
        <div className="card-header bg-primary text-white">
          <h4 className="mb-0">{isEditing ? 'Edit Product' : 'Add New Product'}</h4>
        </div>
        <div className="card-body">
          {error && <div className="alert alert-danger">{error}</div>}
          {successMsg && <div className="alert alert-success">{successMsg}</div>}

          <form onSubmit={handleSubmit} className="row g-3">
            <div className="col-md-6">
              <label className="form-label">Product Name</label>
              <input
                type="text"
                className="form-control"
                name="name"
                value={currentForm.name}
                onChange={handleChange}
                required
              />
            </div>

            <div className="col-md-6">
              <label className="form-label">Category</label>
              <input
                type="text"
                className="form-control"
                name="category"
                value={currentForm.category}
                onChange={handleChange}
                required
              />
            </div>

            <div className="col-12">
              <label className="form-label">Description</label>
              <textarea
                className="form-control"
                name="description"
                value={currentForm.description}
                onChange={handleChange}
                rows="2"
              />
            </div>

            <div className="col-md-4">
              <label className="form-label">Unit Price (₹)</label>
              <input
                type="number"
                className="form-control"
                name="unitPrice"
                value={currentForm.unitPrice}
                onChange={handleChange}
                required
              />
            </div>

            <div className="col-md-4">
              <label className="form-label">Stock</label>
              <input
                type="number"
                className="form-control"
                name="stock"
                value={currentForm.stock}
                onChange={handleChange}
                required
              />
            </div>

            <div className="col-md-4">
              <label className="form-label">Image</label>
              <input type="file" className="form-control" onChange={handleImageUpload} />
              {currentForm.imageBase64 && (
                <img src={currentForm.imageBase64} alt="Preview" className="mt-2 img-thumbnail" height={60} width={60} />
              )}
            </div>

            <div className="col-12">
              <button type="submit" className="btn btn-success">
                {isEditing ? 'Update Product' : 'Add Product'}
              </button>
              {isEditing && (
                <button
                  type="button"
                  className="btn btn-secondary ms-2"
                  onClick={() => {
                    setFormUpdate(initialProduct);
                    setIsEditing(false);
                  }}
                >
                  Cancel
                </button>
              )}
            </div>
          </form>
        </div>
      </div>

      <div className="card shadow-sm">
        <div className="card-header bg-dark text-white">
          <h5 className="mb-0">Product List</h5>
        </div>
        <div className="card-body table-responsive">
          <table className="table table-hover align-middle">
            <thead className="table-light">
              <tr>
                <th>Name</th>
                <th>Category</th>
                <th>Description</th>
                <th>Price (₹)</th>
                <th>Stock</th>
                <th>Image</th>
                <th style={{ minWidth: 120 }}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {products.length ? (
                products.map((p) => (
                  <tr key={p.id}>
                    <td>{p.name}</td>
                    <td>{p.category}</td>
                    <td>{p.description}</td>
                    <td>{p.unitPrice}</td>
                    <td>{p.stock}</td>
                    <td>
                      {p.imageBase64 && (
                        <img src={p.imageBase64} alt={p.name} height={50} className="img-thumbnail" />
                      )}
                    </td>
                    <td>
                      <button className="btn btn-sm btn-warning me-2" onClick={() => handleEdit(p)}>
                        Edit
                      </button>
                      <button className="btn btn-sm btn-danger" onClick={() => handleDelete(p.id)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="7" className="text-center text-muted">
                    No products available.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default ProductCrudPage;
