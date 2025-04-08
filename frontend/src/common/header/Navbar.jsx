import React, { useState, useEffect } from "react"
import { Link } from "react-router-dom"
import AuthService from "../../components/Autentification/authservice";

const Navbar = () => {
  // Toogle Menu
  const [MobileMenu, setMobileMenu] = useState(false)
  
  // États pour suivre l'authentification et le rôle
  const [isAdmin, setIsAdmin] = useState(AuthService.isAdmin());
  const [isAuth, setIsAuth] = useState(AuthService.checkAuthStatus());
  
  // Fonction pour vérifier et mettre à jour le statut d'authentification
  const checkAuthStatus = () => {
    setIsAuth(AuthService.checkAuthStatus());
    setIsAdmin(AuthService.isAdmin());
  };
  
  // Effet pour écouter les changements d'authentification
  useEffect(() => {
    // Vérifier au chargement initial
    checkAuthStatus();
    
    // Écouter les événements d'authentification
    window.addEventListener('authStateChanged', checkAuthStatus);
    
    // Vérifier périodiquement (solution de secours)
    const interval = setInterval(checkAuthStatus, 1000);
    
    // Nettoyage
    return () => {
      window.removeEventListener('authStateChanged', checkAuthStatus);
      clearInterval(interval);
    };
  }, []);

  return (
    <>
      <header className='header'>
        <div className='container d_flex'>
          <div className='catgrories d_flex'>
            {/* <span class='fa-solid fa-border-all'></span> */}
            {/* <h4>
              Categories <i className='fa fa-chevron-down'></i>
            </h4> */}
          </div>

          <div className='navlink'>
            <ul className={MobileMenu ? "nav-links-MobileMenu" : "link f_flex capitalize"} onClick={() => setMobileMenu(false)}>
              {/*<ul className='link f_flex uppercase {MobileMenu ? "nav-links-MobileMenu" : "nav-links"} onClick={() => setMobileMenu(false)}'>*/}
              <li>
                <Link to='/'>Acceuil</Link>
              </li>
              <li>
                <Link to='/shop'>Shop</Link>
              </li>
              <li>
                <Link to='/contact'>Contact</Link>
              </li>
              
              {/* Afficher le lien Dashboard uniquement pour les administrateurs connectés */}
              {isAdmin && (
                <li>
                  <Link to='/product'>Dashboard</Link>
                </li>
              )}
            </ul>

            <button className='toggle' onClick={() => setMobileMenu(!MobileMenu)}>
              {MobileMenu ? <i className='fas fa-times close home-btn'></i> : <i className='fas fa-bars open'></i>}
            </button>
          </div>
        </div>
      </header>
    </>
  )
}

export default Navbar