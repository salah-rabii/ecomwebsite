package com.myecommerce.repository;

import java.util.List;
import com.myecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indique que cette interface est un composant Spring qui gère l'accès aux données
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Recherche des produits par nom (insensible à la casse)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Compter les produits dans une catégorie donnée par l'ID de la catégorie
    long countByCategory_Id(Long categoryId);

    // Vérifie si un produit existe pour une catégorie
    boolean existsByCategory_Id(Long categoryId);

    // Recherche par intervalle de prix
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // Recherche par stock minimum
    List<Product> findByStockGreaterThan(int stock);

    // Tri par prix croissant
    List<Product> findAllByOrderByPriceAsc();

    // Tri par prix décroissant
    List<Product> findAllByOrderByPriceDesc();

    // Tri par nom croissant
    List<Product> findAllByOrderByNameAsc();

    List<Product> findByGenderIgnoreCase(String gender);



}
