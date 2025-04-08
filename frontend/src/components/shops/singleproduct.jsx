import React, { useState } from "react";

import "./style.css";

const Singleproduct = ({ addToCart, shopItems }) => {
  const [quantity, setQuantity] = useState(1);
 

  return (
    <>
      <div className=" product-container ">
        <div className="product-image-section ">
          {/* Image Section */}
          <div className="product-thumbnails  product-image-s">
            {/* Thumbnails */}
            <div className=" product-thumbnails ">
              <img src="../images/shops/shop-1.png" alt="" />
            </div>
            
           
          </div>

          {/* Product Info Section */}
          <div className="product-info-section">
            <h2 className="product-title">Pocket Cotton Sweatshirt</h2>
            <p className="product-description">
              Nam tempus turpis at metus scelerisque placerat nulla deumantos solicitud felis...
            </p>
            
            <div className="delivery-badge">
              <span>FREE DELIVERY</span>
            </div>
            
            <div className="flex items-center gap-3">
              <span className="product-price-original">$629.99</span>
              <span className="product-price-current">$495.00</span>
            </div>
            
            {/* Star Rating */}
            <div className="product-rating">
              {[...Array(4)].map((_, i) => (
                <i key={i} className="fas fa-star"></i>
              ))}
              <i className="fas fa-star-half-alt"></i>
            </div>
            
            {/* Color Selection */}
            <div className="mt-2">
              <span className="color-selection-title">Select Color:</span>
              <div className="color-options">
                {["#e54e5d", "#252525", "#60b3f3"].map((color, index) => (
                  <div 
                    key={index} 
                    className="w-8 h-8 rounded-full cursor-pointer border-2 border-transparent hover:border-gray-300 transition-all"
                    style={{ backgroundColor: color }}
                  ></div>
                ))}
              </div>
            </div>
            
            {/* Quantity & Cart */}
            <div className="quantity-control">
              <span className="quantity-label">Quantity:</span>
              <div className="quantity-selector">
                <button 
                  className="quantity-button"
                  onClick={() => setQuantity(Math.max(1, quantity - 1))}
                >
                  -
                </button>
                <span className="quantity-value">{quantity}</span>
                <button 
                  className="quantity-button"
                  onClick={() => setQuantity(quantity + 1)}
                >
                  +
                </button>
              </div>
            </div>
            
            <button 
              className="add-to-cart-button"
            //   onClick={() => addToCart()}
            >
              ADD TO CART
            </button>
            
          </div>
        </div>
      </div>
    </>
  );
};

export default Singleproduct;