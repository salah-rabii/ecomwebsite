import React, { useState, useEffect } from "react";
import { useHistory, useLocation } from "react-router-dom";
import axios from "axios";
import { loadStripe } from '@stripe/stripe-js';
import AuthService from '../../components/Autentification/authservice';
import "./style.css";

// Charger la clé publique Stripe
const stripePromise = loadStripe('pk_test_51R5sK4FaTQqzl2W9tQuV2BidxI5yLZYCYVNmz6zAMunSMdWkQa5S9GhV5bCn3buKHs6U6h3SKZ2u76UUyN8cLnpI004sYlyHrF');

const Checkout = ({ CartItem }) => {
  const history = useHistory();
  const location = useLocation();
  // Si CartItem est passé en props, utilisez-le, sinon essayez de le récupérer depuis location.state
  const cartItems = CartItem || (location.state && location.state.cartItems) || [];
  
  // États pour gérer le statut d'authentification
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  
  // Calcul du prix total
  const totalPrice = cartItems.reduce(
    (price, item) => price + item.qty * item.price,
    0
  );
  
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  
  // Vérifier l'état d'authentification au chargement
  useEffect(() => {
    const checkAuth = () => {
      const authStatus = AuthService.checkAuthStatus();
      setIsAuthenticated(authStatus);
    };
    
    // Vérifier au chargement
    checkAuth();
    
    // Écouter les changements d'authentification
    const handleAuthChange = () => {
      checkAuth();
    };
    
    window.addEventListener('authStateChanged', handleAuthChange);
    
    // Nettoyage
    return () => {
      window.removeEventListener('authStateChanged', handleAuthChange);
    };
  }, []);

  // Fonction pour rediriger vers la page de connexion
  const redirectToLogin = () => {
    // Sauvegarder l'intention de checkout pour revenir après connexion
    sessionStorage.setItem('redirectAfterLogin', '/checkout');
    history.push('/signin');
  };

  // Fonction pour gérer le checkout
  const handleCheckout = async () => {
    if (cartItems.length === 0) {
      setError("Votre panier est vide");
      return;
    }
    
    // Vérifier l'authentification avant de procéder
    if (!isAuthenticated) {
      setError("Veuillez vous connecter ou créer un compte pour finaliser votre achat");
      return;
    }

    setIsLoading(true);
    setError(null);

    try {
      // Préparer les données pour la requête Stripe
      const productRequest = {
        name: "Achat d'articles",
        currency: "USD",
        amount: totalPrice * 100, // Stripe attend le montant en centimes
        quantity: 1,
      };

      console.log("Envoi de la demande de paiement:", productRequest);

      // Appel à l'API de checkout avec options CORS explicites
      const response = await axios.post(
        "http://localhost:8080/api/product/v1/checkout", 
        productRequest,
        {
          headers: {
            'Content-Type': 'application/json',
          },
          withCredentials: true  // Important: envoyer les cookies pour l'authentification
        }
      );

      console.log("Réponse reçue:", response.data);

      if (response.data && response.data.sessionId) {
        // Utiliser l'API Stripe pour rediriger l'utilisateur vers la page de paiement
        const stripe = await stripePromise;
        const { error } = await stripe.redirectToCheckout({
          sessionId: response.data.sessionId,
        });

        if (error) {
          console.error("Erreur Stripe:", error);
          setError(`Erreur de redirection Stripe: ${error.message}`);
        }
      } else if (response.data && response.data.sessionUrl) {
        // Redirection directe si sessionUrl est fourni
        window.location.href = response.data.sessionUrl;
      } else {
        console.error("Format de réponse invalide:", response.data);
        setError("Format de réponse invalide du serveur");
      }
    } catch (err) {
      console.error("Erreur lors du paiement:", err);
      
      // Vérifier si l'erreur est liée à l'authentification
      if (err.response && err.response.status === 401) {
        setError("Votre session a expiré. Veuillez vous reconnecter.");
        setTimeout(redirectToLogin, 2000); // Rediriger après 2 secondes
      } else {
        // Afficher plus de détails sur l'erreur
        const errorMessage = err.response 
          ? `Erreur ${err.response.status}: ${JSON.stringify(err.response.data)}` 
          : `Erreur: ${err.message}`;
        
        setError(`Une erreur est survenue lors du traitement de votre paiement: ${errorMessage}`);
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="checkout-container">
      <h2>Récapitulatif de la commande</h2>
      
      {!isAuthenticated && (
        <div className="auth-required-message">
          <p>Vous devez être connecté pour finaliser votre achat.</p>
          <div className="auth-buttons">
            <button className="login-button" onClick={redirectToLogin}>
              Se connecter
            </button>
            <button className="register-button" onClick={() => history.push('/register')}>
              Créer un compte
            </button>
          </div>
        </div>
      )}
      
      {cartItems.length === 0 ? (
        <div className="empty-cart">
          <p className="empty-cart-message">Votre panier est vide</p>
          <button className="return-button" onClick={() => history.push('/produit')}>
            Retourner à la boutique
          </button>
        </div>
      ) : (
        <>
          <div className="checkout-items">
            {cartItems.map((item) => (
              <div className="checkout-item" key={item.id}>
                <div className="item-info">
                  <img src={item.imageUrl} alt={item.name} className="item-thumbnail" />
                  <div className="item-details">
                    <h3>{item.name}</h3>
                    <p className="item-price">
                      ${item.price}.00 x {item.qty} = ${item.price * item.qty}.00
                    </p>
                  </div>
                </div>
              </div>
            ))}
          </div>

          <div className="checkout-summary">
            <div className="checkout-total">
              <h3>Total:</h3>
              <h3 className="total-price">${totalPrice}.00</h3>
            </div>

            {error && <p className="error-message">{error}</p>}

            <div className="checkout-actions">
              <button 
                className="back-to-cart" 
                onClick={() => history.push('/cart')}
              >
                Retour au panier
              </button>
              
              <button
                className="pay-button"
                onClick={handleCheckout}
                disabled={isLoading || !isAuthenticated}
              >
                {isLoading ? "Traitement en cours..." : "Procéder au paiement"}
              </button>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default Checkout;