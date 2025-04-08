
// import axios from 'axios';

// const API_BASE_URL = 'http://localhost:8080/api/products';

// export const addProduct = async (productData) => {
//   try {
//     const response = await axios.post(API_BASE_URL, productData);
//     return response.data;
//   } catch (error) {
//     console.error('Error adding product:', error.response || error);
//     throw error;
//   }
// };

// export const updateProduct = async (productId, productData) => {
//   try {
//     const response = await axios.put(`${API_BASE_URL}/${productId}`, productData);
//     return response.data;
//   } catch (error) {
//     console.error('Error updating product:', error.response || error);
//     throw error;
//   }
// };

// export const deleteProduct = async (productId) => {
//   try {
//     await axios.delete(`${API_BASE_URL}/${productId}`);
//     return true;
//   } catch (error) {
//     console.error('Error deleting product:', error.response || error);
//     throw error;
//   }
// };

// export const getAllProducts = async () => {
//   try {
//     const response = await fetch(`${API_BASE_URL}?size=100`);
    
//     // Log status for debugging
//     console.log("Response status:", response.status);

//     // Check if response is ok (status 200-299)
//     if (!response.ok) {
//       // Try to get text content to see the error
//       const textContent = await response.text();
//       console.error("Error response:", textContent);
//       throw new Error(`HTTP error! Status: ${response.status}`);
//     }

//     // Only try to parse JSON if we got a successful response
//     return await response.json();
//   } catch (error) {
//     console.error("Error fetching products:", error);
//     throw error;
//   }
// };

// // Remove or fix the allProducts function since getAllProducts is better implemented
// // If you need to keep both for backward compatibility:
// export const allProducts = async () => {
//   return getAllProducts();
// };


import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/products';

// Add authentication token if needed
const getAuthHeader = () => {
  const token = localStorage.getItem('token');
  return token ? { Authorization: `Bearer ${token}` } : {};
};

// Function to add a new product
export const addProduct = async (productData) => {
  try {
    const response = await fetch('/api/products', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(productData)
    });

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error('Error adding product:', error);
    throw error;
  }
};

// Function to delete a product
export const deleteProduct = async (productId) => {
  try {
    const response = await fetch(`/api/products/${productId}`, {
      method: 'DELETE',
    });

    console.log(response);

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    return true;
  } catch (error) {
    console.error(error);
    console.error('Error deleting product:', error);
    throw error;
  }
};

// Function to update a product
export const updateProduct = async (productId, productData) => {
  try {
    const response = await fetch(`/api/products/${productId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(productData)
    });

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error('Error updating product:', error);
    throw error;
  }
};

export const getAllProducts = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}?size=100`, {
      headers: getAuthHeader()
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching products:", error.response?.data || error.message);
    throw new Error(error.response?.data?.message || 'Failed to fetch products');
  }
};

// For backward compatibility
export const allProducts = async () => {
  return getAllProducts();
};






// Method to count products in a specific category
export const countProductsByCategory = async (categoryId) => {
   try {
     const response = await axios.get(`${API_BASE_URL}/count-by-category/${categoryId}`, {
     headers: getAuthHeader()
   });
     return response.data;
    } catch (error) {
      console.error(`Error counting products in category ${categoryId}:`, error.response?.data || error.message);
       if (error.response?.status === 404) {
  
        return 0; // Or handle the not found case as needed
   }
   throw new Error(error.response?.data?.message || `Failed to count products in category ${categoryId}`);
   }
  };
  

  // Function to search products by name
export const searchProductsByName = async (name) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/search`, {
      params: { name },
      headers: getAuthHeader(),
    });
    return response.data;
  } catch (error) {
    console.error("Error searching products by name:", error.response?.data || error.message);
    throw new Error(error.response?.data?.message || 'Failed to search products by name');
  }
};

