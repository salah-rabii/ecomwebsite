package com.myecommerce.repository;

import com.myecommerce.model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
    // ✅ Décommente cette méthode
    @Query("SELECT l FROM LigneCommande l JOIN FETCH l.produit WHERE l.id = :id")
    Optional<LigneCommande> findByIdWithProduit(@Param("id") Long id);


    @Query("SELECT l FROM LigneCommande l JOIN FETCH l.produit WHERE l.commande.id = :commandeId")
    List<LigneCommande> findByCommandeId(@Param("commandeId") Long commandeId);

    @Query("SELECT l FROM LigneCommande l JOIN FETCH l.produit WHERE l.produit.id = :produitId")
    List<LigneCommande> findByProduitId(@Param("produitId") Long produitId);

    @Query("SELECT l.prixUnitaire * l.quantite FROM LigneCommande l WHERE l.id = :id")
    Optional<BigDecimal> calculateTotalForLigne(@Param("id") Long ligneCommandeId);

    /*@Query("SELECT l FROM LigneCommande l JOIN FETCH l.produit WHERE l.id = :id")
    Optional<LigneCommande> findByIdWithProduit(@Param("id") Long id);*/
}