
package com.myecommerce.controller;

import com.myecommerce.DTO.LigneCommandeRequestDTO;
import com.myecommerce.DTO.LigneCommandeResponseDTO;
import com.myecommerce.exception.NotFoundException;
import com.myecommerce.model.Commande;
import com.myecommerce.model.LigneCommande;
import com.myecommerce.model.Product;
import com.myecommerce.service.LigneCommandeService;
import com.myecommerce.repository.CommandeRepository;
import com.myecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ligne-commande")
public class LigneCommandeController {

    private final LigneCommandeService ligneCommandeService;
    private final CommandeRepository commmandeRepository;
    private final ProductRepository produitRepository;

    @Autowired
    public LigneCommandeController(LigneCommandeService ligneCommandeService, CommandeRepository commmandeRepository, ProductRepository produitRepository) {
        this.ligneCommandeService = ligneCommandeService;
        this.commmandeRepository = commmandeRepository;
        this.produitRepository = produitRepository;
    }

    @PostMapping
    public ResponseEntity<LigneCommandeResponseDTO> createLigneCommande(
            @RequestBody LigneCommandeRequestDTO requestDTO) {
        requestDTO.validate();

        LigneCommande createdLigne = ligneCommandeService.createCompleteLigneCommande(
                requestDTO.getCommandeId(),
                requestDTO.getProduitId(),
                requestDTO.getQuantite()
        );

        return new ResponseEntity<>(convertToDto(createdLigne), HttpStatus.CREATED);
    }
    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<LigneCommandeResponseDTO>> getByCommandeId(
            @PathVariable Long commandeId) {
        List<LigneCommande> lignes = ligneCommandeService.getLignesByCommandeId(commandeId);
        return ResponseEntity.ok(lignes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneCommandeResponseDTO> getById(@PathVariable Long id) {
        LigneCommande ligne = ligneCommandeService.getLigneById(id);
        return ResponseEntity.ok(convertToDto(ligne));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneCommandeResponseDTO> updateLigneCommande(
            @PathVariable Long id,
            @RequestBody LigneCommandeRequestDTO requestDTO) {
        requestDTO.validate();
        LigneCommande ligneUpdates = convertToEntity(requestDTO); // Cette méthode doit inclure la mise à jour des objets liés (Produit, Commande)
        LigneCommande updatedLigne = ligneCommandeService.updateLigneCommande(id, ligneUpdates);

        return ResponseEntity.ok(convertToDto(updatedLigne));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable Long id) {
        ligneCommandeService.deleteLigneCommande(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<LigneCommandeResponseDTO>> getByProduitId(@PathVariable Long produitId) {
        try {
            List<LigneCommande> lignes = ligneCommandeService.getLignesByProduitId(produitId);
            if (lignes.isEmpty()) {
                throw new NotFoundException("Lignes de commande pour le produit", produitId);
            }
            return ResponseEntity.ok(lignes.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/{id}/total")
    public ResponseEntity<BigDecimal> calculateTotal(@PathVariable Long id) {
        BigDecimal total = ligneCommandeService.calculateTotalForLigne(id);
        return ResponseEntity.ok(total);
    }
    private LigneCommande convertToEntity(LigneCommandeRequestDTO dto) {
        LigneCommande ligne = new LigneCommande();
        ligne.setQuantite(dto.getQuantite());

        // Récupérer le produit et la commande depuis les ID
        Product produit = produitRepository.findById(dto.getProduitId()).orElseThrow(() -> new NotFoundException("Produit", dto.getProduitId()));
        Commande commande = commmandeRepository.findById(dto.getCommandeId()).orElseThrow(() -> new NotFoundException("Commande", dto.getCommandeId()));

        ligne.setProduit(produit);
        ligne.setCommande(commande);
        ligne.setPrixUnitaire(produit.getPrice()); // Utiliser le prix du produit actuel

        return ligne;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private LigneCommandeResponseDTO convertToDto(LigneCommande ligne) {
        if (ligne.getProduit() == null) {
            throw new IllegalArgumentException("Aucun produit trouvé pour la ligne de commande ID: " + ligne.getId());
        }

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