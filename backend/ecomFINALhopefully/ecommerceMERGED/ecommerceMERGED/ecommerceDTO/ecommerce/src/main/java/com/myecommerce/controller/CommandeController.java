package com.myecommerce.controller;

import com.myecommerce.DTO.*;
import com.myecommerce.model.LigneCommande;
import com.myecommerce.model.Commande;
import com.myecommerce.model.StatutCommande;
import com.myecommerce.service.CommandeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commandes")

public class CommandeController {

    private final CommandeService commandeService;

    @Autowired
    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }
    @PostMapping("/panier/{utilisateurId}")
    public ResponseEntity<CommandeResponseDTO> creerPanier(@PathVariable Long utilisateurId) {
        Commande panier = commandeService.creerPanier(utilisateurId);
        return new ResponseEntity<>(convertToDto(panier), HttpStatus.CREATED);
    }


    @PostMapping("/{commandeId}/ajouter-produit")
    public ResponseEntity<CommandeResponseDTO> ajouterProduit(
            @PathVariable Long commandeId,
            @RequestBody @Valid LigneCommandeAjoutDTO request) {

        // Comme le DTO ne contient pas commandeId, on utilise directement commandeId fourni dans l'URL.
        Commande commande = commandeService.ajouterProduitAuPanier(
                commandeId,
                request.getProduitId(),
                request.getQuantite()
        );
        return ResponseEntity.ok(convertToDto(commande));
    }


    @PostMapping("/{commandeId}/passer-commande")
    public ResponseEntity<CommandeResponseDTO> passerCommande(@PathVariable Long commandeId) {
        Commande commande = commandeService.passerCommande(commandeId);
        return ResponseEntity.ok(convertToDto(commande));
    }
    @PostMapping("/{commandeId}/annuler")
    public ResponseEntity<CommandeResponseDTO> annulerCommande(@PathVariable Long commandeId) {
        Commande commande = commandeService.annulerCommande(commandeId);
        return ResponseEntity.ok(convertToDto(commande));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommandeResponseDTO> getCommande(@PathVariable Long id) {
        Commande commande = commandeService.trouverCommande(id);
        return ResponseEntity.ok(convertToDto(commande));
    }
    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<CommandeResponseDTO>> getCommandesUtilisateur(
            @PathVariable Long utilisateurId) {
        List<Commande> commandes = commandeService.getCommandesUtilisateur(utilisateurId);
        return ResponseEntity.ok(commandes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{commandeId}/statut")
    public ResponseEntity<CommandeResponseDTO> updateStatut(
            @PathVariable Long commandeId,
            @RequestParam StatutCommande statut) {
        Commande commande = commandeService.mettreAJourStatut(commandeId, statut);
        return ResponseEntity.ok(convertToDto(commande));
    }




    private CommandeResponseDTO convertToDto(Commande commande) {
        CommandeResponseDTO dto = new CommandeResponseDTO();
        dto.setId(commande.getCommandeId());
        dto.setDate(commande.getDate());
        dto.setStatut(commande.getStatut());
        dto.setMontantTotal(commande.getMontantTotal());
        List<LigneCommandeResponseDTO> lignesDto = commande.getLigneCommandes().stream()
                .map(this::convertLigneToDto)
                .collect(Collectors.toList());
        dto.setLignes(lignesDto);

        return dto;
    }

    private LigneCommandeResponseDTO convertLigneToDto(LigneCommande ligne) {
        LigneCommandeResponseDTO dto = new LigneCommandeResponseDTO();
        dto.setId(ligne.getId());
        dto.setProduitId(ligne.getProduit().getId());
        dto.setProduitNom(ligne.getProduit().getName());
        dto.setQuantite(ligne.getQuantite());
        dto.setPrixUnitaire(ligne.getPrixUnitaire());
        dto.setTotalLigne(ligne.getPrixUnitaire().multiply(BigDecimal.valueOf(ligne.getQuantite())));

        return dto;
    }

}
