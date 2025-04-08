import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import "./style.css";
import Logout from '../Autentification/logout';
import { addProduct,deleteProduct,updateProduct } from '../../services/productService';
import EditProductForm from '../product/EditProductForm';

const ProductManagement = ({ shopItems, setShopItems }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [isEditOpen, setIsEditOpen] = useState(false);
  const [currentProduct, setCurrentProduct] = useState(null);
  const [newProduct, setNewProduct] = useState({
    name: '',
    price: '',
    category: '',
    description: '',
    discount: '0',
    gender: '',
    image_url: './images/shops/shop-1.png', // Default image
    size: 'M',
    stock: 10
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10); // Number of items per page

  const togglePopup = () => {
    setIsOpen(!isOpen);
    // Reset form when opening/closing
    setNewProduct({
      name: '',
      price: '',
      category: '',
      description: '',
      discount: '0',
      gender: '',
      image_url: './images/shops/shop-1.png',
      size: 'M',
      stock: 10
    });
    setError(null);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewProduct({
      ...newProduct,
      [name]: name === 'price' || name === 'stock' ? Number(value) : value
    });
  };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     setLoading(true);
//     setError(null);

//     // Map category to gender for consistency with your data model
//     let genderValue = 'unisex';
//     if (newProduct.category === 'men') genderValue = 'men';
//     if (newProduct.category === 'women') genderValue = 'women';
//     if (newProduct.category === 'kids') genderValue = 'kids';

//     // Create product DTO to match your backend expectations
//     const productDTO = {
//       name: newProduct.name,
//       price: Number(newProduct.price),
//       description: newProduct.description,
//       discount: Number(newProduct.discount),
//       gender: genderValue,
//       image_url: newProduct.image_url,
//       size: newProduct.size,
//       stock: Number(newProduct.stock),
//       categoryId: 2 // Using default category ID from your sample data
//     };

//     try {
//       const response = await fetch('/api/products', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(productDTO)
//       });

//       if (!response.ok) {
//         throw new Error(`Error: ${response.status}`);
//       }

//       const addedProduct = await response.json();

//       // Update the local state with the new product
//       setShopItems([...shopItems, addedProduct]);

//       // Close the popup
//       togglePopup();
//     } catch (err) {
//       setError(`Failed to add product: ${err.message}`);
//     } finally {
//       setLoading(false);
//     }
//   };



const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
  
    // Map category to gender for consistency with your data model
    let genderValue = 'unisex';
    if (newProduct.category === 'men') genderValue = 'men';
    if (newProduct.category === 'women') genderValue = 'women';
    if (newProduct.category === 'kids') genderValue = 'kids';
  
    // Create product DTO to match your backend expectations
    const productDTO = {
      name: newProduct.name,
      price: Number(newProduct.price),
      description: newProduct.description,
      discount: Number(newProduct.discount),
      gender: genderValue,
      image_url: newProduct.image_url,
      size: newProduct.size,
      stock: Number(newProduct.stock),
      categoryId: 2 // Using default category ID from your sample data
    };
  
    try {
      // Use the service function instead of direct fetch
      
      const addedProduct = await addProduct(productDTO);
      
      // Update the local state with the new product
      setShopItems([...shopItems, addedProduct]);
  
      // Close the popup
      togglePopup();
    } catch (err) {
      setError(`Failed to add product: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  const handleEditClick = (product) => {
    setCurrentProduct(product);
    setIsEditOpen(true);
  };

  const handleProductUpdated = (updatedProduct) => {
    // Update the product in the shopItems array
    setShopItems(
      shopItems.map(item =>
        item.id === updatedProduct.id ? updatedProduct : item
      )
    );
  };

  const handleDeleteClick = async (productId) => {
    if (window.confirm('Are you sure you want to delete this product?')) {
      try {
        await deleteProduct(productId);
        // Remove the product from the local state
        setShopItems(shopItems.filter(item => item.id !== productId));
      } catch (err) {
        alert(`Failed to delete product: ${err.message}`);
      }
    }
  };

  // Pagination logic
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = shopItems.slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className='dashboard'>
      <div className='first'>
        <div className="sidebar">
          <h2>Dashboard</h2>
          {/* <Link to="/dashboard">Dashboard</Link> */}
          <Link to="/product">Products</Link>
          <Link to="/countproduct">CountProduct</Link>
          <Link to="/users">Users</Link>
          <Logout/>
        </div>
      </div>
      <div className='second'>
        <div className="table-container">
          <div className='end'>
            <button className='edit btn-dash' onClick={togglePopup}>Add Product</button>
            {isOpen && (
              <div className="popup-overlay">
                <div className="popup-content">
                  {error && <div className="error-message">{error}</div>}
                  <form onSubmit={handleSubmit}>
                    <div className="form-group">
                      <label htmlFor="name">Name</label>
                      <input
                        type="text"
                        name="name"
                        id="name"
                        placeholder="Type product name"
                        value={newProduct.name}
                        onChange={handleInputChange}
                        required
                      />
                    </div>

                    <div className="form-group">
                      <label htmlFor="price">Price</label>
                      <input
                        type="number"
                        name="price"
                        id="price"
                        placeholder="$2999"
                        value={newProduct.price}
                        onChange={handleInputChange}
                        required
                      />
                    </div>

                    <div className="form-group">
                      <label htmlFor="category">Category</label>
                      <select
                        id="category"
                        name="category"
                        value={newProduct.category}
                        onChange={handleInputChange}
                        required
                      >
                        <option value="">Select category</option>
                        <option value="men">Men</option>
                        <option value="women">Women</option>
                        <option value="kids">Kids</option>
                      </select>
                    </div>

                    <div className="form-group">
                      <label htmlFor="description">Description</label>
                      <textarea
                        id="description"
                        name="description"
                        rows="4"
                        placeholder="Enter product description here"
                        value={newProduct.description}
                        onChange={handleInputChange}
                        required
                      ></textarea>
                    </div>

                    <div className="form-group">
                      <label htmlFor="discount">Discount</label>
                      <select
                        id="discount"
                        name="discount"
                        value={newProduct.discount}
                        onChange={handleInputChange}
                      >
                        <option value="0">No</option>
                        <option value="5">5%</option>
                        <option value="10">10%</option>
                        <option value="20">20%</option>
                        <option value="30">30%</option>
                        <option value="40">40%</option>
                        <option value="50">50%</option>
                      </select>
                    </div>

                    <div className="form-group">
                      <label htmlFor="stock">Stock</label>
                      <input
                        type="number"
                        name="stock"
                        id="stock"
                        placeholder="10"
                        value={newProduct.stock}
                        onChange={handleInputChange}
                        required
                      />
                    </div>

                    <div className="form-buttons">
                      <button
                        type="submit"
                        className="btn primary"
                        disabled={loading}
                      >
                        {loading ? 'Adding...' : 'Add product'}
                      </button>
                      <button
                        type="button"
                        className="btn cancel"
                        onClick={togglePopup}
                      >
                        <svg
                          aria-hidden="true"
                          className="icon"
                          fill="none"
                          stroke="currentColor"
                          viewBox="0 0 24 24"
                        >
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                        </svg>
                        Cancel
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            )}
          </div>
          <h2>Product Management</h2>
          <table className="custom-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Category</th>
                <th>Stock</th>
                <th>Discount</th>
                <th>Modify</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              {currentItems.map((product) => (
                <tr key={product.id}>
                  <td>#{product.id}</td>
                  <td>{product.name}</td>
                  <td>${product.price}</td>
                  <td>{product.gender || "N/A"}</td>
                  <td>{product.stock || 0}</td>
                  <td>{product.discount || 0}%</td>
                  <td>
                    <button
                      className="btn-dash edit"
                      onClick={() => handleEditClick(product)}
                    >
                      <i className="fas fa-edit"></i>
                    </button>
                  </td>
                  <td>
                    <button
                      className="btn-dash red"
                      onClick={() => handleDeleteClick(product.id)}
                    >
                      <i className="fa-solid fa-trash"></i>
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <div className="pagination">
            {Array.from({ length: Math.ceil(shopItems.length / itemsPerPage) }, (_, index) => (
              <button
                key={index}
                onClick={() => paginate(index + 1)}
                className={currentPage === index + 1 ? 'active' : ''}
              >
                {index + 1}
              </button>
            ))}
          </div>
        </div>
      </div>

      {isEditOpen && currentProduct && (
        <EditProductForm
          product={currentProduct}
          onClose={() => setIsEditOpen(false)}
          onProductUpdated={handleProductUpdated}
        />
      )}
    </div>
  );
};

export default ProductManagement;
