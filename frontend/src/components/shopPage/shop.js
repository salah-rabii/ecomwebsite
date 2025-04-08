import React from 'react';

import Shop from "../shops/Shop"; // Import Shop component

const Shopp= ({ shopItems = [], addToCart , CartItem,  decreaseQty , addToWishlist, removeFromWishlist, isInWishlist,  wishlistItems }) => {
  return <Shop shopItems={shopItems} addToCart={addToCart} 
  addToWishlist={addToWishlist}
  removeFromWishlist={removeFromWishlist}
  isInWishlist={isInWishlist}/>;
};

export default Shopp;
