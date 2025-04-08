// import React, { useState, useEffect } from 'react';
// import { updateProduct } from '../../services/productService';

// const EditProductForm = ({ product, onClose, onProductUpdated }) => {
//   const [editedProduct, setEditedProduct] = useState({
//     name: product.name,
//     price: product.price,
//     description: product.description,
//     discount: product.discount,
//     gender: product.gender,
//     image_url: product.image_url,
//     size: product.size,
//     stock: product.stock,
//     categoryId: product.categoryId || 2
//   });
//   const [loading, setLoading] = useState(false);
//   const [error, setError] = useState(null);

//   // Map gender to category for the form
//   const getCategory = (gender) => {
//     if (gender === 'men') return 'men';
//     if (gender === 'women') return 'women';
//     if (gender === 'kids') return 'kids';
//     return '';
//   };

//   useEffect(() => {
//     setEditedProduct({
//       name: product.name,
//       price: product.price,
//       description: product.description,
//       discount: product.discount,
//       gender: product.gender,
//       image_url: product.image_url,
//       size: product.size,
//       stock: product.stock,
//       categoryId: product.categoryId || 2
//     });
//   }, [product]);

//   const handleInputChange = (e) => {
//     const { name, value } = e.target;
//     setEditedProduct({
//       ...editedProduct,
//       [name]: name === 'price' || name === 'stock' || name === 'discount' ? Number(value) : value
//     });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     setLoading(true);
//     setError(null);

//     // Map category to gender for consistency with your data model
//     let genderValue = editedProduct.gender;
//     if (e.target.category) {
//       if (e.target.category.value === 'men') genderValue = 'men';
//       if (e.target.category.value === 'women') genderValue = 'women';
//       if (e.target.category.value === 'kids') genderValue = 'kids';
//     }

//     // Create product DTO to match your backend expectations
//     const productDTO = {
//       id: product.id,
//       name: editedProduct.name,
//       price: Number(editedProduct.price),
//       description: editedProduct.description,
//       discount: Number(editedProduct.discount),
//       gender: genderValue,
//       image_url: editedProduct.image_url,
//       size: editedProduct.size,
//       stock: Number(editedProduct.stock),
//       categoryId: editedProduct.categoryId
//     };

//     try {
//       const updatedProduct = await updateProduct(product.id, productDTO);
//       onProductUpdated(updatedProduct);
//       onClose();
//     } catch (err) {
//       setError(`Failed to update product: ${err.message}`);
//     } finally {
//       setLoading(false);
//     }
//   };

//   return (
//     <div className="popup-overlay">
//       <div className="popup-content">
//         <h3>Edit Product</h3>
//         {error && <div className="error-message">{error}</div>}
//         <form onSubmit={handleSubmit}>
//           <div className="form-group">
//             <label htmlFor="name">Name</label>
//             <input
//               type="text"
//               name="name"
//               id="name"
//               value={editedProduct.name}
//               onChange={handleInputChange}
//               required
//             />
//           </div>

//           <div className="form-group">
//             <label htmlFor="price">Price</label>
//             <input
//               type="number"
//               name="price"
//               id="price"
//               value={editedProduct.price}
//               onChange={handleInputChange}
//               required
//             />
//           </div>

//           <div className="form-group">
//             <label htmlFor="category">Category</label>
//             <select
//               id="category"
//               name="category"
//               value={getCategory(editedProduct.gender)}
//               onChange={handleInputChange}
//               required
//             >
//               <option value="">Select category</option>
//               <option value="men">Men</option>
//               <option value="women">Women</option>
//               <option value="kids">Kids</option>
//             </select>
//           </div>

//           <div className="form-group">
//             <label htmlFor="description">Description</label>
//             <textarea
//               id="description"
//               name="description"
//               rows="4"
//               value={editedProduct.description}
//               onChange={handleInputChange}
//               required
//             ></textarea>
//           </div>

//           <div className="form-group">
//             <label htmlFor="discount">Discount</label>
//             <select
//               id="discount"
//               name="discount"
//               value={editedProduct.discount}
//               onChange={handleInputChange}
//             >
//               <option value="0">No</option>
//               <option value="5">5%</option>
//               <option value="10">10%</option>
//               <option value="20">20%</option>
//               <option value="30">30%</option>
//               <option value="40">40%</option>
//               <option value="50">50%</option>
//             </select>
//           </div>

//           <div className="form-group">
//             <label htmlFor="stock">Stock</label>
//             <input
//               type="number"
//               name="stock"
//               id="stock"
//               value={editedProduct.stock}
//               onChange={handleInputChange}
//               required
//             />
//           </div>

//           <div className="form-buttons">
//             <button
//               type="submit"
//               className="btn primary"
//               disabled={loading}
//             >
//               {loading ? 'Updating...' : 'Update product'}
//             </button>
//             <button
//               type="button"
//               className="btn cancel"
//               onClick={onClose}
//             >
//               Cancel
//             </button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// };

// export default EditProductForm;



import React, { useState } from 'react';
import { updateProduct } from '../../services/productService';

const EditProductForm = ({ product, onClose, onProductUpdated }) => {
  const [formData, setFormData] = useState({
    id: product.id,
    name: product.name || '',
    price: product.price || 0,
    gender: product.gender || '',
    description: product.description || '',
    discount: product.discount || 0,
    image_url: product.image_url || './images/shops/shop-1.png',
    size: product.size || 'M',
    stock: product.stock || 10,
    categoryId: product.categoryId || 2
  });
  
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: name === 'price' || name === 'stock' || name === 'discount' 
        ? Number(value) : value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const updatedProduct = await updateProduct(product.id, formData);
      onProductUpdated(updatedProduct);
      onClose();
    } catch (err) {
      setError(`Failed to update product: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="popup-overlay">
      <div className="popup-content">
        <h2>Edit Product</h2>
        {error && <div className="error-message">{error}</div>}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="edit-name">Name</label>
            <input
              type="text"
              name="name"
              id="edit-name"
              value={formData.name}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="edit-price">Price</label>
            <input
              type="number"
              name="price"
              id="edit-price"
              value={formData.price}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="edit-gender">Category</label>
            <select
              id="edit-gender"
              name="gender"
              value={formData.gender}
              onChange={handleInputChange}
              required
            >
              <option value="">Select category</option>
              <option value="men">Men</option>
              <option value="women">Women</option>
              <option value="kids">Kids</option>
              <option value="unisex">Unisex</option>
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="edit-description">Description</label>
            <textarea
              id="edit-description"
              name="description"
              rows="4"
              value={formData.description}
              onChange={handleInputChange}
              required
            ></textarea>
          </div>

          <div className="form-group">
            <label htmlFor="edit-discount">Discount</label>
            <select
              id="edit-discount"
              name="discount"
              value={formData.discount}
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
            <label htmlFor="edit-stock">Stock</label>
            <input
              type="number"
              name="stock"
              id="edit-stock"
              value={formData.stock}
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
              {loading ? 'Updating...' : 'Update product'}
            </button>
            <button
              type="button"
              className="btn cancel"
              onClick={onClose}
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditProductForm;