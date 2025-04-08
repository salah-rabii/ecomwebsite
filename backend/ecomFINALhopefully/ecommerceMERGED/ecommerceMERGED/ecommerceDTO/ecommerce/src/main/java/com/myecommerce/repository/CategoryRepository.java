package com.myecommerce.repository;

import com.myecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Rechercher une catégorie par nom partiel (insensible à la casse)
    List<Category> findByNameContainingIgnoreCase(String name);

    // Compter le nombre de produits dans une catégorie
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId")
    long countProductsByCategoryId(@Param("categoryId") Long categoryId);

    // Vérifier l'existence d'une catégorie par son nom
    boolean existsByName(String name);

    // Vérifier l'existence d'une catégorie par son ID
    boolean existsById(Long id);
}
