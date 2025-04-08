import React, { useState } from 'react';
import { withRouter } from 'react-router-dom';
import AuthService from './authservice';
import './style.css';

const Logout = ({ history, onLogoutSuccess }) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleLogout = async () => {
    setLoading(true);
    setError(null);

    try {
      await AuthService.logout();
      
      // Déclencher un événement personnalisé pour informer les autres composants
      window.dispatchEvent(new CustomEvent('authStateChanged'));
      
      // Appeler le callback de succès si fourni
      if (onLogoutSuccess && typeof onLogoutSuccess === 'function') {
        onLogoutSuccess();
      }
      
      history.replace('/signin');
    } catch (error) {
      console.error('Déconnexion échouée:', error);
      setError('La déconnexion a échoué. Veuillez réessayer.');
      
      // Même en cas d'erreur, on déclenche l'événement car le sessionStorage est effacé
      window.dispatchEvent(new CustomEvent('authStateChanged'));
      
      // Même en cas d'erreur, on peut rediriger
      history.replace('/signin');
    } finally {
      setLoading(false);
    }
  };

  return (
    <button 
      onClick={handleLogout} 
      className="btn btn-sm btn-danger"
      style={{
        fontSize: '0.7rem',
        padding: '2px 6px',
        whiteSpace: 'nowrap',
        minWidth: 'max-content'
      }}
    >
      {loading ? '...' : 'Déconnexion'}
    </button>
  );
};

export default withRouter(Logout);