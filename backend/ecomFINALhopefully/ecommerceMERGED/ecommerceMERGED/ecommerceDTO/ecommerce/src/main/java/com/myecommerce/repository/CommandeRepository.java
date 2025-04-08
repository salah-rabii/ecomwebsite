package com.myecommerce.repository;

import com.myecommerce.model.AppUser;
import com.myecommerce.model.Commande;
import com.myecommerce.model.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    @Query("SELECT DISTINCT c FROM Commande c " +
            "JOIN FETCH c.ligneCommandes lc " +
            "JOIN FETCH lc.produit " +
            "WHERE c.utilisateur.id = :userId")
    List<Commande> findByUtilisateurId(@Param("userId") Long userId);

    List<Commande> findByStatut(StatutCommande statut);

    @Query("SELECT c FROM Commande c LEFT JOIN FETCH c.ligneCommandes lc LEFT JOIN FETCH lc.produit WHERE c.commandeId = :id")
    Optional<Commande> findById(@Param("id") Long id);




    @Query("SELECT COUNT(c), SUM(c.montantTotal) FROM Commande c " +
            "WHERE c.date BETWEEN :start AND :end AND c.statut = :statut")
    Object[] countCommandesAndRevenueByPeriod(
            @Param("start") LocalDateTime startDate,
            @Param("end") LocalDateTime endDate,
            @Param("statut") StatutCommande statut);
    @Query("SELECT c FROM Commande c JOIN FETCH c.ligneCommandes WHERE c.utilisateur = :utilisateur AND c.statut = :statut")
    List<Commande> findByUtilisateurAndStatut(
            @Param("utilisateur") AppUser utilisateur,
            @Param("statut") StatutCommande statut);


    @Query("SELECT c FROM Commande c WHERE c.utilisateur = :utilisateur AND c.statut = :statut")
    Optional<Commande> findSingleByUtilisateurAndStatut(
            @Param("utilisateur") AppUser utilisateur,
            @Param("statut") StatutCommande statut);


    @Query("SELECT c FROM Commande c JOIN FETCH c.ligneCommandes WHERE c.utilisateur = :utilisateur AND c.statut <> :statut")
    List<Commande> findByUtilisateurAndStatutNot(
            @Param("utilisateur") AppUser utilisateur,
            @Param("statut") StatutCommande statut);


    @Query("SELECT c FROM Commande c JOIN FETCH c.ligneCommandes WHERE c.utilisateur = :utilisateur AND c.statut <> :statut ORDER BY c.date DESC")
    List<Commande> findByUtilisateurAndStatutNotOrderByDateDesc(
            @Param("utilisateur") AppUser utilisateur,
            @Param("statut") StatutCommande statut);

}