package com.myecommerce.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commandeId;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommande statut;

    @Column(name = "montant_total", precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser utilisateur;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> ligneCommandes = new ArrayList<>();

    public Commande() {}

    public void passerCommande() {
        if (ligneCommandes == null || ligneCommandes.isEmpty()) {
            throw new IllegalStateException("Impossible de passer une commande vide");
        }

        ligneCommandes.forEach(ligne -> {
            if (ligne.getProduit().getStock() < ligne.getQuantite()) {
                throw new IllegalStateException("Stock insuffisant pour le produit: " + ligne.getProduit().getName());
            }
            ligne.getProduit().reduireStock(ligne.getQuantite());
        });

        this.statut = StatutCommande.EN_COURS;
        this.date = LocalDateTime.now();
        calculerTotal();
    }





    public void calculerTotal() {
        if (ligneCommandes == null || ligneCommandes.isEmpty()) {
            this.montantTotal = BigDecimal.ZERO;
        } else {
            this.montantTotal = ligneCommandes.stream()
                    .map(ligne -> {
                        BigDecimal prixUnitaire = ligne.getProduit().getPrice();
                        return prixUnitaire.multiply(BigDecimal.valueOf(ligne.getQuantite()));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public void annulerCommande() {
        if(this.statut!=StatutCommande.LIVREE){
            this.statut=StatutCommande.ANNULEE;
            ligneCommandes.forEach(ligne -> {
                Product p = ligne.getProduit();
                p.ajouterStock(ligne.getQuantite());
            });
        }
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public StatutCommande getStatut() {
        return statut;
    }

    public void setStatut(StatutCommande statut) {
        this.statut = statut;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public AppUser getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(AppUser utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    public void mettreAJourStatut(StatutCommande nouveauStatut) {
        this.statut = nouveauStatut;
    }

    public Commande(Long commandeId, LocalDateTime date, StatutCommande statut, BigDecimal montantTotal, AppUser utilisateur, List<LigneCommande> ligneCommandes) {
        this.commandeId = commandeId;
        this.date = date;
        this.statut = statut;
        this.montantTotal = montantTotal;
        this.utilisateur = utilisateur;
        this.ligneCommandes = ligneCommandes;
    }
}