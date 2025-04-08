import { Link } from "react-router-dom";

import React from 'react';
// import "./style.css";

const Wishlist = ({ CartItem, addToCart, decreaseQty, addToWishlist, removeFromWishlist, isInWishlist, wishlistItems }) => {
  const totalPrice = CartItem.reduce((price, item) => price + item.qty * item.price, 0)
  console.log("Wishlist items:", wishlistItems); // Add this for debugging



  return (
    <div >

     

      <section className='cart-items'>
        <div className='container d_flexx'>
          {/* if hamro cart ma kunai pani item xaina bhane no diplay */}

          <div className='cart-details'>
            {wishlistItems.length === 0 && <h1 className='no-items product'>No Items are add in Wishlist</h1>}
            
            {/* yasma hami le cart item lai display garaaxa */}
            {wishlistItems.map((item) => {
              const productQty = item.price * item.qty

              return (
                <div className='cart-list product d_flex' key={item.id}>
                  <div className='img'>
                    <img src={item.imageUrl} alt='' />
                  </div>
                  <div className='cart-details'>
                    <h3>{item.name}</h3>
                    <p>${item.price}</p>
                    
                  </div>
                  <div className='cart-items-function'>
                    <div className='removeCart'>
                      <button className='removeCart'>
                        <i className='fa-solid fa-xmark'></i>
                      </button>
                    </div>
                    {/* stpe: 5 
                    product ko qty lai inc ra des garne
                    */}
                    <div className='cartControl d_flex'>
                      
                      <button className='desCart' onClick={() => removeFromWishlist(item)}>
                        <i className='fa-solid fa-minus'></i>
                      </button>
                    </div>
                  </div>

                  <div className='cart-item-price'></div>
                </div>
              )
            })}
          </div>

          
        </div>


      </section>
    </div>
  );
};

export default Wishlist;
