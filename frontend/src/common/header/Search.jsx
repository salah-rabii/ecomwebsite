import React, { useState, useEffect } from "react"
import logo from "../../components/assets/images/logo.png"
import { Link } from "react-router-dom"
import AuthService from "../../components/Autentification/authservice"
import Logout from "../../components/Autentification/logout"

const Search = ({ CartItem }) => {
  // État local pour suivre le statut d'authentification
  const [isAuthenticated, setIsAuthenticated] = useState(AuthService.checkAuthStatus());
  const [isAdminUser, setIsAdminUser] = useState(AuthService.isAdmin());

  // Fonction pour vérifier et mettre à jour le statut d'authentification
  const checkAuthStatus = () => {
    const authStatus = AuthService.checkAuthStatus();
    const adminStatus = AuthService.isAdmin();
    setIsAuthenticated(authStatus);
    setIsAdminUser(adminStatus);
  };

  // Effet qui s'exécute à chaque montage du composant et qui ajoute un écouteur d'événement
  useEffect(() => {
    // Vérifier au chargement
    checkAuthStatus();
    
    // Créer un événement personnalisé pour la mise à jour de l'authentification
    window.addEventListener('authStateChanged', checkAuthStatus);
    
    // Vérifier toutes les secondes (solution de secours)
    const interval = setInterval(checkAuthStatus, 1000);
    
    // fixed Header (code existant)
    const scrollHandler = function() {
      const search = document.querySelector(".search")
      search.classList.toggle("active", window.scrollY > 100)
    };
    window.addEventListener("scroll", scrollHandler);

    // Nettoyage lors du démontage
    return () => {
      window.removeEventListener('authStateChanged', checkAuthStatus);
      window.removeEventListener("scroll", scrollHandler);
      clearInterval(interval);
    };
  }, []);

  return (
    <>
      <section className='search'>
        <div className='container c_flex'>
          <div className='logo width'>
            <a href="/">
            <img src={logo} alt='' className="mr-3" />
            </a>
          </div>

          <div className='search-box f_flex'>
            <i className='fa fa-search'></i>
            <input type='text' placeholder='Search and hit enter...' />
            <span>All Category</span>
          </div>

          <div className='icon f_flex width'>
            <Link to='/wishlist'>
              <i className='fa-regular fa-heart icon-circle'></i>
            </Link>
            
            <div className="user-icon-container" style={{ position: 'relative' }}>
              <Link to={isAuthenticated ? '/product' : '/register'}>
                <i className='fa fa-user icon-circle'></i>
                <span 
                  style={{ 
                    position: 'absolute', 
                    top: '-5px', 
                    right: '-5px', 
                    width: '10px', 
                    height: '10px', 
                    borderRadius: '50%', 
                    backgroundColor: isAuthenticated ? '#28a745' : '#dc3545', 
                  }} 
                />
              </Link>
              
              {isAuthenticated && !isAdminUser && (
                <div style={{ 
                  position: 'absolute', 
                  bottom: '-30px', 
                  left: '50%', 
                  transform: 'translateX(-50%)',
                  zIndex: 1000
                }}>
                  <Logout onLogoutSuccess={checkAuthStatus} />
                </div>
              )}
            </div>
            
            <div className='cart'>
              <Link to='/cart'>
                <i className='fa fa-shopping-bag icon-circle'></i>
                <span>{CartItem.length === 0 ? "0" : CartItem.length}</span>
              </Link>
            </div>
          </div>
        </div>
      </section>
    </>
  )
}

export default Search