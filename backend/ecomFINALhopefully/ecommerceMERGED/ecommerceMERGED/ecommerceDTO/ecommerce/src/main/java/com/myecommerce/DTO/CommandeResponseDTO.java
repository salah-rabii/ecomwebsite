package com.myecommerce.DTO;

import com.myecommerce.model.StatutCommande;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CommandeResponseDTO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private StatutCommande statut;
    private BigDecimal montantTotal;
    private List<LigneCommandeResponseDTO> lignes;
    public CommandeResponseDTO() {
    }

    public CommandeResponseDTO(Long id, LocalDateTime date, StatutCommande statut,
                               BigDecimal montantTotal, List<LigneCommandeResponseDTO> lignes) {
        this.id = id;
        this.date = date;
        this.statut = statut;
        this.montantTotal = montantTotal;
        this.lignes = lignes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<LigneCommandeResponseDTO> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeResponseDTO> lignes) {
        this.lignes = lignes;
    }
}
