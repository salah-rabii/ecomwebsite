package com.myecommerce.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class LigneCommandeRequestDTO {
    @NotNull(message = "L'ID produit est obligatoire")
    private Long produitId;

    @Min(value = 1, message = "La quantité doit être supérieure à 0")
    private int quantite;

    @NotNull(message = "L'ID commande est obligatoire")
    private Long commandeId;


    public LigneCommandeRequestDTO() {
    }

    public LigneCommandeRequestDTO(Long produitId,Long commandeId, int quantite) {
        this.produitId = Objects.requireNonNull(produitId, "L'ID produit ne peut pas être null");
        this.quantite = quantite;
        this.commandeId = Objects.requireNonNull(commandeId, "L'ID commande ne peut pas être null");
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public void validate() {
        if (produitId == null || produitId <= 0) {
            throw new IllegalArgumentException("L'ID produit est obligatoire et doit être positif");
        }
        if (commandeId == null || commandeId <= 0) {
            throw new IllegalArgumentException("L'ID commande est obligatoire et doit être positif");
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0");
        }
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