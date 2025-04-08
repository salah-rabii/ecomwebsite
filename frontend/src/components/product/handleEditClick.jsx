// This is just the relevant part to add to AdminDashboard.jsx

// Add these imports at the top
import { deleteProduct } from '../../services/productService';
import EditProductForm from './EditProductForm';

// Add these state variables inside AdminDashboard component
const [isEditOpen, setIsEditOpen] = useState(false);
const [currentProduct, setCurrentProduct] = useState(null);

// Add these functions inside AdminDashboard component

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


// Modify the buttons in the table rows
{shopItems.map((product) => (
  <tr key={product.id}>
    <td>#{product.id}</td>
    <td>{product.name}</td>
    <td>${product.price}</td>
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

// Add this after the add product popup
{isEditOpen && currentProduct && (
  <EditProductForm
    product={currentProduct}
    onClose={() => setIsEditOpen(false)}
    onProductUpdated={handleProductUpdated}
  />
)}