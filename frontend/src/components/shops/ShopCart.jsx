import React  from "react";
import { Link } from "react-router-dom";

const ShopCart = ({ shopItems, addToCart, CartItem,  decreaseQty , addToWishlist, removeFromWishlist, isInWishlist,  wishlistItems }) => {
    
  
    // Save wishlist items to localStorage whenever they change
  


  console.log( isInWishlist);


  return (
    <>
      {shopItems.map((item, index) => {
        const wishlisted = isInWishlist(item.id); 
        return (
          <div className='box' key={item.id}>
            <div className='product mtop'>
              <div className='img'>
                <span className='discount'>{item.discount}% Off</span>
                <Link to={`/product/${item.id}`}>
                  <img src={item.imageUrl} alt={item.name} />
                </Link>
             
                <div className='product-like'>
                <i
                  className={isInWishlist(item.id) ? "fa-solid fa-heart" : "fa-regular fa-heart"}
                  onClick={() => 
                    isInWishlist(item.id) 
                      ? removeFromWishlist(item) 
                      : addToWishlist(item)
                  }
                  style={{ 
                    color: isInWishlist(item.id) ? "red" : "",
                    cursor: "pointer" 
                  }}
                ></i>              
                  </div>
              </div>
              <div className='product-details'>
                <h3>
                  <Link to={`/product/${item.id}`}>{item.name}</Link>
                </h3>
                <div className='rate'>
                  <i className='fa fa-star'></i>
                  <i className='fa fa-star'></i>
                  <i className='fa fa-star'></i>
                  <i className='fa fa-star'></i>
                  <i className='fa fa-star'></i>
                </div>
                <div className='price'>
                  <h4>${item.price}.00 </h4>
                  <button onClick={() => addToCart(item)}>
                    <i className='fa fa-plus'></i>
                  </button>
                </div>
                
              </div>
            </div>
            
          </div>
        );
      })}
    </>
  );
};

export default ShopCart;