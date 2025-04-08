

// export default App
import React, { useState, useEffect } from "react"
import "./App.css"

import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom"
import Header from "./common/header/Header"
import Pages from "./pages/Pages"
import Data from "./components/Data"
import Cart from './common/Cart/Cart';
import Footer from "./common/footer/Footer"
import Sdata from "./components/shops/Sdata"
import Shopp from './components/shopPage/shop'
import Singleproduct from './components/shops/singleproduct'
import Single from './components/shops/single'
import Register from "./components/Autentification/register"
import Signin from "./components/Autentification/Signin"
import Wishlist from "./components/wishlist/wishlist"
import Dashboard from "./components/Dashboard/dashboard"
import ProductManagement from "./components/Dashboard/ProductManagement"
import Users from "./components/users/users"
import ContactPage from "./components/contact/ContactPage"
import Checkout from "./common/payement/checkout"
import SuccessPage from "./common/payement/Success"
import CancelPage from "./common/payement/Cancel"

// Import auth service
import AuthService from "./components/Autentification/authservice"

// Import the API service
import { getAllProducts } from './services/productService.js';
import ProductListWithCategoryCount from "./components/product/ProductListWithCategoryCount.jsx"

const ProtectedRoute = ({ children, ...rest }) => {
  const isAuthenticated = AuthService.checkAuthStatus();
  
  return (
    <Route
      {...rest}
      render={({ location }) =>
        isAuthenticated ? (
          children
        ) : (
          <Redirect
            to={{
              pathname: "/signin",
              state: { from: location }
            }}
          />
        )
      }
    />
  );
};

// Admin-only route
const AdminRoute = ({ children, ...rest }) => {
  const isAuthenticated = AuthService.checkAuthStatus();
  const isAdmin = AuthService.isAdmin();

  return (
    <Route
      {...rest}
      render={({ location }) =>
        isAuthenticated && isAdmin ? (
          children
        ) : (
          <Redirect
            to={{
              pathname: !isAuthenticated ? "/signin" : "/",
              state: { from: location }
            }}
          />
        )
      }
    />
  );
};

function App() {
  // State for cart and wishlist
  const [CartItem, setCartItem] = useState([])
  const [wishlistItems, setWishlistItems] = useState([])

  // State for products with API integration
  const [shopItems, setShopItems] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  // Default product items from local data
  const { productItems } = Data

  // UI state
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [sidebarColor, setSidebarColor] = useState('primary');
  const [sidebarType, setSidebarType] = useState('bg-transparent');
  const [navbarFixed, setNavbarFixed] = useState(false);

  // Fetch products from API on component mount
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const data = await getAllProducts();

        if (data && Array.isArray(data)) {
          // Handle direct array response
          setShopItems(data);
        } else if (data && data.content && Array.isArray(data.content)) {
          // Handle paginated response with content property
          setShopItems(data.content);
        } else if (data) {
          // Handle other response formats
          console.warn("Unexpected API response format:", data);
          setShopItems([]); 
        } else {
          // Fall back to local data if API returns nothing
          setShopItems(Sdata.shopItems);
          console.warn("API returned no data, using fallback data");
        }
      } catch (err) {
        console.error("API call failed with error:", err);
        setError(`Failed to load products: ${err.message}`);
        // Fall back to local data on error
        setShopItems(Sdata.shopItems);
      } finally {
        setLoading(false);
      }
    };
    fetchProducts();
  }, []);

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  // Cart functions
  const addToCart = (product) => {
    const productExit = CartItem.find((item) => item.id === product.id)
    if (productExit) {
      setCartItem(CartItem.map((item) => (item.id === product.id ? { ...productExit, qty: productExit.qty + 1 } : item)))
    } else {
      setCartItem([...CartItem, { ...product, qty: 1 }])
    }
  }

  const decreaseQty = (product) => {
    const productExit = CartItem.find((item) => item.id === product.id)
    if (productExit.qty === 1) {
      setCartItem(CartItem.filter((item) => item.id !== product.id))
    } else {
      setCartItem(CartItem.map((item) => (item.id === product.id ? { ...productExit, qty: productExit.qty - 1 } : item)))
    }
  }

  // Wishlist functions
  const addToWishlist = (product) => {
    const productExist = wishlistItems.find((item) => item.id === product.id);
    if (!productExist) {
      setWishlistItems([...wishlistItems, product]);
    }
  };

  const removeFromWishlist = (product) => {
    setWishlistItems(wishlistItems.filter((item) => item.id !== product.id));
  };

  const isInWishlist = (productId) => {
    return wishlistItems.some(item => item.id === productId);
  };

  // Function to update shopItems when products are modified in the admin dashboard
  const updateShopItems = (updatedItems) => {
    setShopItems(updatedItems);
  };

  return (
    <>
      <Router>
        <Header CartItem={CartItem} wishlistItems={wishlistItems} />
       

        <Switch>
          <Route path='/' exact>
            <Pages
              productItems={productItems}
              addToCart={addToCart}
              shopItems={shopItems}
              addToWishlist={addToWishlist}
              removeFromWishlist={removeFromWishlist}
              isInWishlist={isInWishlist}
              loading={loading}
            />
          </Route>

          <Route path='/cart' exact>
            <Cart
              shopItems={shopItems}
              addToCart={addToCart}
              addToWishlist={addToWishlist}
              removeFromWishlist={removeFromWishlist}
              isInWishlist={isInWishlist}
              CartItem={CartItem}
              decreaseQty={decreaseQty}
            />
          </Route>

          <Route path='/shop' exact>
            <Shopp
              shopItems={shopItems}
              addToCart={addToCart}
              addToWishlist={addToWishlist}
              removeFromWishlist={removeFromWishlist}
              isInWishlist={isInWishlist}
              CartItem={CartItem}
              decreaseQty={decreaseQty}
              loading={loading}
            />
          </Route>

          <Route path='/single' exact>
            <Singleproduct
              addToCart={addToCart}
              shopItems={shopItems}
              addToWishlist={addToWishlist}
              removeFromWishlist={removeFromWishlist}
              isInWishlist={isInWishlist}
              loading={loading}
            />
          </Route>

          <Route path='/register' exact>
            <Register />
          </Route>

          <AdminRoute path='/dashboard' exact>
            <Dashboard
              shopItems={shopItems}
              setShopItems={updateShopItems}
              sidebarOpen={sidebarOpen}
              toggleSidebar={toggleSidebar}
              sidebarColor={sidebarColor}
              setSidebarColor={setSidebarColor}
              sidebarType={sidebarType}
              setSidebarType={setSidebarType}
              navbarFixed={navbarFixed}
              setNavbarFixed={setNavbarFixed}
            />
          </AdminRoute>

          <AdminRoute path='/product' exact>
            <ProductManagement
              shopItems={shopItems}
              setShopItems={updateShopItems}
            />
          </AdminRoute>

          <AdminRoute path='/users' exact>
            <Users shopItems={shopItems} />
          </AdminRoute>

          <Route path='/signin' exact>
            <Signin />
          </Route>

          <Route path="/contact">
            <ContactPage />
          </Route>

          <ProtectedRoute path='/wishlist' exact>
            <Wishlist
              shopItems={shopItems}
              addToCart={addToCart}
              addToWishlist={addToWishlist}
              removeFromWishlist={removeFromWishlist}
              isInWishlist={isInWishlist}
              CartItem={CartItem}
              decreaseQty={decreaseQty}
              wishlistItems={wishlistItems}
            />
          </ProtectedRoute>

          <Route path="/countproduct"exact >
             <ProductListWithCategoryCount/>
          </Route>
          

          <Route path="/product/:id" exact render={(props) =>
            <Single
              {...props}
              addToCart={addToCart}
              shopItems={shopItems}
              addToWishlist={addToWishlist}
              removeFromWishlist={removeFromWishlist}
              isInWishlist={isInWishlist}
              loading={loading}
            />}
          />

          <ProtectedRoute path='/checkout' exact>
            <Checkout CartItem={CartItem} />
          </ProtectedRoute>

          <Route path='/success' exact>
            <SuccessPage />
          </Route>

          <Route path='/cancel' exact>
            <CancelPage />
          </Route>
        </Switch>
        <Footer />
      </Router>
    </>
  )
}

export default App