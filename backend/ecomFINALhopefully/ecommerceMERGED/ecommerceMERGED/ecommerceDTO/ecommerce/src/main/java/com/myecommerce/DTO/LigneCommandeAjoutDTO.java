package com.myecommerce.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LigneCommandeAjoutDTO {

    @NotNull(message = "L'ID produit est obligatoire")
    private Long produitId;

    @Min(value = 1, message = "La quantité doit être supérieure à 0")
    private int quantite;

    public LigneCommandeAjoutDTO() {
    }

    public LigneCommandeAjoutDTO(Long produitId, int quantite) {
        this.produitId = produitId;
        this.quantite = quantite;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
