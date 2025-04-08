import React from 'react';
import { useHistory } from 'react-router-dom';

const CancelPage = () => {
  const history = useHistory();

  const handleReturn = () => {
    history.push('/cart');
  };

  return (
    <div className="container mt-5 text-center">
      <div className="cancel-container" style={{
        maxWidth: "600px",
        margin: "50px auto",
        padding: "40px 20px",
        borderRadius: "8px",
        boxShadow: "0 4px 20px rgba(0, 0, 0, 0.1)",
        backgroundColor: "#fff"
      }}>
        <div className="cancel-icon" style={{
          fontSize: "60px",
          color: "#e94560",
          marginBottom: "20px"
        }}>
          <i className="fas fa-times-circle"></i>
          {/* Alternative if fa icons not available */}
        </div>
        
        <h1>Payment Cancelled</h1>
        <p style={{ fontSize: "18px", color: "#666", margin: "20px 0" }}>
          Your payment has been cancelled. Please try again or contact support if you have any questions.
        </p>
        
        <button 
          className="btn btn-primary btn-lg" 
          onClick={handleReturn}
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
          Return to Cart
        </button>
      </div>
    </div>
  );
};

export default CancelPage;