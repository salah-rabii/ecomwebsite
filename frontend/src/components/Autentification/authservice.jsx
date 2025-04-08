import axios from 'axios';

const AuthService = {
  login: async (email, password) => {
    try {
      const response = await axios.post(
        'http://localhost:8080/api/account/login', 
        { email, password },
        { withCredentials: true }
      );
      
      // Stocker une indication que l'utilisateur est connecté
      sessionStorage.setItem('isLoggedIn', 'true');
      
      // Stocker le rôle de l'utilisateur, en nettoyant si nécessaire
      if (response.data && response.data.role) {
        let role = response.data.role;
        // Si le rôle est au format [ROLE], nettoyez-le
        if (role.startsWith('[') && role.endsWith(']')) {
          role = role.substring(1, role.length - 1);
        }
        sessionStorage.setItem('userRole', role);
        console.log("Rôle stocké:", role); // Pour débogage
      }
      
      // Déclencher un événement pour informer les autres composants
      window.dispatchEvent(new CustomEvent('authStateChanged'));
      
      return response.data;
    } catch (error) {
      throw error;
    }
  },
  
  logout: async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/account/logout', {
        withCredentials: true
      });
      
      // Supprimer les informations de session
      sessionStorage.removeItem('isLoggedIn');
      sessionStorage.removeItem('userRole');
      
      // Déclencher un événement pour informer les autres composants
      window.dispatchEvent(new CustomEvent('authStateChanged'));
      
      return response.data;
    } catch (error) {
      // Même en cas d'erreur, on supprime les indications locales
      sessionStorage.removeItem('isLoggedIn');
      sessionStorage.removeItem('userRole');
      
      // Déclencher un événement même en cas d'erreur
      window.dispatchEvent(new CustomEvent('authStateChanged'));
      
      throw error;
    }
  },
  
  checkAuthStatus: () => {
    return sessionStorage.getItem('isLoggedIn') === 'true';
  },
  
  getUserRole: () => {
    return sessionStorage.getItem('userRole') || 'USER';
  },
  
  isAdmin: () => {
    const role = sessionStorage.getItem('userRole');
    // console.log("Vérification du rôle:", role); // Pour débogage
    return role === 'ADMIN' || role === 'ROLE_ADMIN'; // Adaptez selon votre système de rôles
  }

};

export default AuthService;