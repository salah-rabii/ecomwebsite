package com.myecommerce.model;

public enum StatutCommande {
    PANIER,
    EN_COURS,
    PAYEE,
    EXPEDIEE,
    LIVREE,
    RETOURNEE,
    ANNULEE;

    // Méthode utilitaire pour vérifier si une commande peut être modifiée
    public boolean peutEtreModifiee() {
        return this == PANIER || this == EN_COURS;
    }
}