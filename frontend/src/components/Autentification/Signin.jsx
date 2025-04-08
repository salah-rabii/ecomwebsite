import React, { useState } from 'react';
import { withRouter } from 'react-router-dom';
import AuthService from './authservice';
import './style.css';

const Signin = ({ history }) => {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [message, setMessage] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const response = await AuthService.login(formData.email, formData.password);
      console.log("Réponse complète:", response); // Debugging
      setMessage(response.message || "Connexion réussie");
      
      // Vérifier s'il y a une redirection à effectuer après la connexion
      const redirectPath = sessionStorage.getItem('redirectAfterLogin');
      if (redirectPath) {
        sessionStorage.removeItem('redirectAfterLogin'); // Nettoyer après utilisation
        history.replace(redirectPath);
      } else {
        // Redirection par défaut
        history.replace('/product');
      }
    } catch (error) {
      setError(error.response?.data?.error || "Erreur de connexion");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-container">
      <div className="rows">
      
        <div className="bg col-6">
          <div className="register-header">
            <h1>Welcome Back !</h1>
            <img src="./images/image.png" alt="" className="image" />
            <p>
              Don't you have an account? <a className="hreff" href="/register">Create One</a>
            </p>
          </div>
        </div>
        <div className="col-6">
          <form onSubmit={handleSubmit} className="form">
                  {error && <div className="alert alert-danger">{error}</div>}
                  {message && <div className="alert alert-success">{message}</div>}
            <div className="input-box">
              <label>Email Address</label>
              <input
                type="email"
                name="email"
                placeholder="Enter email address"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>
            <div className="input-box">
              <label>Password</label>
              <input
                type="password"
                name="password"
                placeholder="Enter Your password"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>

            <button type="submit" disabled={loading}>
              {loading ? 'Connexion en cours...' : 'Se connecter'}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default withRouter(Signin);