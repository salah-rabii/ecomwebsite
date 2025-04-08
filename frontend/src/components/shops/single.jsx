import React from 'react';
import { useParams } from 'react-router-dom';
// import "./style.css";

const Single = ({ addToCart, shopItems }) => {
  const { id } = useParams();
  console.log("Product ID:", id); // Debugging: Check if ID is correct
//  const [mainImage, setMainImage] = useState("../images/shops/shop-1.png");
  // const [quantity, setQuantity] = useState(1);
 

  const product = shopItems.find(item => item.id === parseInt(id));

  if (!product) return <div>Product not found</div>;

  return (
    <div>
      


      <div className=" product-container ">
        <div className="product-image-section ">
          {/* Image Section */}
          <div className="product-thumbnails  product-image-s">
            {/* Thumbnails */}
            <div className=" product-thumbnails ">
            <img src={`../${product.cover}`} alt="" />
            </div>
            
           
          </div>

          {/* Product Info Section */}
          <div className="product-info-section">
            <h2 className="product-title">{product.name}</h2>
            <p className="product-description">
              Nam tempus turpis at metus scelerisque placerat nulla deumantos solicitud felis...
            </p>
            
            <div className="delivery-badge">
              <span>FREE DELIVERY</span>
            </div>
            
            <div className="flex items-center gap-3">
              <span className="product-price-original">${product.price}.00</span>
              <span className="product-price-current">${product.price}.00</span>
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
              <span className="color-selection-title">Categorie</span>
              <div className="color-options">
               <p>{product.category}</p>
               
              </div>
            </div>
            
            {/* Quantity & Cart */}
           
            
            <button 
              className="add-to-cart-button"
            //   onClick={() => addToCart()}
            >
              ADD TO CART
            </button>
            
          </div>
        </div>
      </div>

      
      
    </div>
    
  );
};

export default Single;
