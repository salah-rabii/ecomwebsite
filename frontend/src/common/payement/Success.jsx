import React from 'react';
import { useHistory } from 'react-router-dom';

const SuccessPage = () => {
  const history = useHistory();

  const handleContinueShopping = () => {
    history.push('/shop');
  };

  return (
    <div className="container mt-5 text-center">
      <div className="success-container" style={{
        maxWidth: "600px",
        margin: "50px auto",
        padding: "40px 20px",
        borderRadius: "8px",
        boxShadow: "0 4px 20px rgba(0, 0, 0, 0.1)",
        backgroundColor: "#fff"
      }}>
        <div className="success-icon" style={{
          fontSize: "60px",
          color: "#4CAF50",
          marginBottom: "20px"
        }}>
          <i className="fas fa-check-circle"></i>
          {/* Alternative if fa icons not available */}
        </div>
        
        <h1>Payment Successful!</h1>
        <p style={{ fontSize: "18px", color: "#666", margin: "20px 0" }}>
          Thank you for your purchase. Your order has been processed successfully.
        </p>
        
        <button 
          className="btn btn-primary btn-lg" 
          onClick={handleContinueShopping}
          style={{
            backgroundColor: "#e94560",
            color: "white",
            border: "none",
            padding: "12px 30px",
            fontSize: "16px",
            borderRadius: "4px",
            cursor: "pointer",
            transition: "background-color 0.3s"
          }}
        >
          Continue Shopping
        </button>
      </div>
    </div>
  );
};

export default SuccessPage;