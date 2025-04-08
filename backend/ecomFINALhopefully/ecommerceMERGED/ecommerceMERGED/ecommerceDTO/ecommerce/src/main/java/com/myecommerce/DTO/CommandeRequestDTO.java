package com.myecommerce.DTO;


import java.util.List;
import java.util.Objects;

public class CommandeRequestDTO {
    private Long utilisateurId;
    private List<LigneCommandeRequestDTO> lignes;

    public CommandeRequestDTO() {
    }

    public CommandeRequestDTO(Long utilisateurId, List<LigneCommandeRequestDTO> lignes) {
        this.utilisateurId = Objects.requireNonNull(utilisateurId, "L'ID utilisateur ne peut pas être null");
        this.lignes = Objects.requireNonNull(lignes, "La liste des lignes ne peut pas être null");
    }
    public void validate() {
        if (utilisateurId == null || utilisateurId <= 0) {
            throw new IllegalArgumentException("L'ID utilisateur est obligatoire et doit être positif");
        }
        if (lignes == null || lignes.isEmpty()) {
            throw new IllegalArgumentException("La commande doit contenir au moins une ligne");
        }
        for (LigneCommandeRequestDTO ligne : lignes) {
            ligne.validate();
        }
        if (lignes.stream().anyMatch(l -> l.getQuantite() <= 0)) {
            throw new IllegalArgumentException("Les quantités doivent être positives");
        }
    }
    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public List<LigneCommandeRequestDTO> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeRequestDTO> lignes) {
        this.lignes = lignes;
    }
}