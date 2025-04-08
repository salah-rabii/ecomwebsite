package com.myecommerce.model;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;


@Entity
@Table(name = "ligne_commande")
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Product produit;

    public Product getLoadedProduit() {
        if(produit != null && Hibernate.isInitialized(produit)) {
            return produit;
        }
        return null; // Ou lancez une exception
    }

    @Column(nullable = false)
    private Integer quantite;

    // Ajout d'un champ prixUnitaire pour historique
    @Column(name = "prix_unitaire", precision = 10, scale = 2)
    private BigDecimal prixUnitaire;

    @PrePersist
    protected void onPrePersist() {
        if (prixUnitaire == null && produit != null) {
            this.prixUnitaire = produit.getPrice();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Product getProduit() {
        return produit;
    }

    public void setProduit(Product produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public LigneCommande() {
    }

    public LigneCommande(Long id, Commande commande, Product produit, Integer quantite, BigDecimal prixUnitaire) {
        this.id = id;
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }
}