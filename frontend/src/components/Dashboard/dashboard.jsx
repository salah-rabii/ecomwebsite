import React, { useEffect, useState } from 'react';
import { withRouter } from 'react-router-dom'; // Use withRouter instead of useNavigate
import Logout from '../Autentification/logout';
import AuthService from '../Autentification/authservice';
import axios from 'axios';
import AdminDashboard from './admindashboard';
import './style.css';
import { Redirect } from 'react-router-dom/cjs/react-router-dom.min';

const Dashboard = ({ history }) => { // Use history from props instead of useNavigate
  const [dashboardData, setDashboardData] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Vérifier si l'utilisateur est connecté
    if (!AuthService.checkAuthStatus()) {
      history.replace('/signin'); // Use history.replace instead of navigate
      return;
    }

    // Déterminer quelle API appeler en fonction du rôle
    const dashboardUrl = AuthService.isAdmin() 
      ? 'http://localhost:8080/api/admin/dashboard'  // API Admin
      : 'http://localhost:8080/api/dashboard';       // API utilisateur standard

    // Charger les données du tableau de bord approprié
    const fetchDashboard = async () => {
      try {
        const response = await axios.get(dashboardUrl, {
          withCredentials: true
        });
        setDashboardData(response.data);
        setError(null);
      } catch (error) {
        console.error("Erreur lors du chargement du tableau de bord:", error);
        setError("Impossible de charger les données du tableau de bord");
        
        // Si erreur 401 ou 403, rediriger vers login
        if (error.response && (error.response.status === 401 || error.response.status === 403)) {
          AuthService.logout();
          history.replace('/signin'); // Use history.replace instead of navigate
        }
      } finally {
        setLoading(false);
      }
    };

    fetchDashboard();
  }, [history]); // Change navigate to history

  if (loading) {
    return <div>Chargement...</div>;
  }

  return (
    <div>
                {AuthService.isAdmin() ? (
            <AdminDashboard shopItems={dashboardData.shopItems || []} />
            ) : (
            <>
                {/* <Logout /> */}
                <Redirect to="/" /> {/* Redirect to the dashboard if not admin */}
            </>
            )}
                </div>
            );
};

export default withRouter(Dashboard); // Wrap with withRouter instead of exporting directly