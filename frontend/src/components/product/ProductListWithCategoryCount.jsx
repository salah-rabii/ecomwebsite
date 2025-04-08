import React, { useState, useEffect } from 'react';
import { getAllProducts, countProductsByCategory } from '../../services/productService';
import './style.css';

const ProductCard = ({ product }) => {
  return (
    <div className="product-card">
      <h3>{product.name}</h3>
      <p>Price: ${product.price}</p>
      {/* Ajoute plus de détails ici si nécessaire */}
    </div>
  );
};

const ProductListWithCategoryCount = () => {
  const [products, setProducts] = useState([]);
  const [categoryCounts, setCategoryCounts] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [allProductsData, kids, men, women] = await Promise.all([
          getAllProducts(),
          countProductsByCategory(1),
          countProductsByCategory(2),
          countProductsByCategory(3),
        ]);

        setProducts(allProductsData.content || allProductsData);
        setCategoryCounts({
          kids,
          men,
          women,
        });
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) return <div className="loader">Chargement des produits...</div>;
  if (error) return <div className="error-message">Erreur : {error}</div>;

  return (

  
    <div className="product-list-container">
      <h2 className='ds'>Tous les Produits</h2>

      <div className="category-count-card">
        <h3>Nombre de produits par catégorie</h3>
        <ul>
          <li>👶 Enfants : {categoryCounts.kids}</li>
          <li>👨 Hommes  : {categoryCounts.men}</li>
          <li>👩 Femmes: {categoryCounts.women}</li>
        </ul>
      </div>

      <div className="product-grid">
        {products.map(product => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </div>

    
  );
};

export default ProductListWithCategoryCount;
