import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import AuthService from "../../components/Autentification/authservice";
import "./style.css";

const Cart = ({ CartItem, addToCart, decreaseQty }) => {
  const history = useHistory();
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  
  // Calculate total price of items
  const totalPrice = CartItem.reduce(
    (price, item) => price + item.qty * item.price, 
    0
  );

  // Check authentication status on component mount
  useEffect(() => {
    const checkAuth = () => {
      const authStatus = AuthService.checkAuthStatus();
      setIsAuthenticated(authStatus);
    };
    
    // Check on load
    checkAuth();
    
    // Listen for auth changes
    const handleAuthChange = () => {
      checkAuth();
    };
    
    window.addEventListener('authStateChanged', handleAuthChange);
    
    // Cleanup
    return () => {
      window.removeEventListener('authStateChanged', handleAuthChange);
    };
  }, []);

  // Handle navigation to checkout
  const goToCheckout = () => {
    if (!isAuthenticated) {
      // Save intent to checkout
      sessionStorage.setItem('redirectAfterLogin', '/checkout');
      history.push('/signin');
    } else {
      history.push('/checkout', { cartItems: CartItem, totalPrice: totalPrice });
    }
  };

  return (
    <>
      <section className="cart-items">
        <div className="container d_flex">
          {/* if cart is empty */}
          <div className="cart-details">
            {CartItem.length === 0 && (
              <h1 className="no-items product">No Items are add in Cart</h1>
            )}

            {/* display cart items */}
            {CartItem.map((item) => {
              const productQty = item.price * item.qty;

              return (
                <div className="cart-list product d_flex" key={item.id}>
                  <div className="img">
                    <img src={item.imageUrl} alt="" />
                  </div>
                  <div className="cart-details">
                    <h3>{item.name}</h3>
                    <h4>
                      ${item.price}.00 * {item.qty}
                      <span>${productQty}.00</span>
                    </h4>
                  </div>
                  <div className="cart-items-function">
                    <div className="removeCart">
                      <button className="removeCart">
                        <i className="fa-solid fa-xmark"></i>
                      </button>
                    </div>
                    {/* product quantity controls */}
                    <div className="cartControl d_flex">
                      <button
                        className="incCart"
                        onClick={() => addToCart(item)}
                      >
                        <i className="fa-solid fa-plus"></i>
                      </button>
                      <button
                        className="desCart"
                        onClick={() => decreaseQty(item)}
                      >
                        <i className="fa-solid fa-minus"></i>
                      </button>
                    </div>
                  </div>

                  <div className="cart-item-price"></div>
                </div>
              );
            })}
          </div>

          <div className="cart-total product">
            <h2>Cart Summary</h2>
            <div className="d_flex">
              <h4>Total Price :</h4>
              <h3>${totalPrice}.00</h3>
            </div>
            
            {!isAuthenticated && CartItem.length > 0 && (
              <div className="auth-message">
                <p>Vous devez être connecté pour finaliser votre achat.</p>
              </div>
            )}
            
            <div>
              {CartItem.length !== 0 && (
                <button 
                  className="buttonCheckout" 
                  onClick={goToCheckout}
                >
                  {isAuthenticated 
                    ? "Proceed to checkout" 
                    : "Se connecter pour acheter"}
                </button>
              )}
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default Cart;