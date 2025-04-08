package com.myecommerce.service;

import com.myecommerce.model.Commande;
import com.myecommerce.model.LigneCommande;
import com.myecommerce.model.Product;
import com.myecommerce.exception.NotFoundException;
import com.myecommerce.exception.BusinessException;
import com.myecommerce.repository.CommandeRepository;
import com.myecommerce.repository.LigneCommandeRepository;
import com.myecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class LigneCommandeService {

    private final LigneCommandeRepository ligneCommandeRepository;
    private final CommandeRepository commandeRepository; // <-- Ajout
    private final ProductRepository produitRepository;

    public LigneCommandeService(LigneCommandeRepository ligneCommandeRepository, CommandeRepository commandeRepository, ProductRepository produitRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.commandeRepository = commandeRepository;
        this.produitRepository = produitRepository;
    }

    public LigneCommande createLigneCommande(LigneCommande ligneCommande) {
        validateLigneCommande(ligneCommande);
        return ligneCommandeRepository.save(ligneCommande);
    }

    public List<LigneCommande> getLignesByCommandeId(Long commandeId) {
        List<LigneCommande> lignes = ligneCommandeRepository.findByCommandeId(commandeId);
        if (lignes.isEmpty()) {
            throw new NotFoundException("Lignes de commande pour la commande", commandeId);
        }
        return lignes;
    }

    public LigneCommande updateLigneCommande(Long id, LigneCommande ligneDetails) {

        // Récupérer la ligne de commande existante
        LigneCommande existingLigne = ligneCommandeRepository.findById(id).orElseThrow(() -> new NotFoundException("Ligne de commande", id));

        // Mettre à jour les informations de la ligne de commande
        if (ligneDetails.getQuantite() != null) {
            existingLigne.setQuantite(ligneDetails.getQuantite());
        }

        if (ligneDetails.getPrixUnitaire() != null) {
            existingLigne.setPrixUnitaire(ligneDetails.getPrixUnitaire());
        }

        // Vérifier si le produit a été changé et mettre à jour
        if (ligneDetails.getProduit() != null && !ligneDetails.getProduit().equals(existingLigne.getProduit())) {
            existingLigne.setProduit(ligneDetails.getProduit());
        }

        // Vérifier si la commande a été changée et mettre à jour
        if (ligneDetails.getCommande() != null && !ligneDetails.getCommande().equals(existingLigne.getCommande())) {
            existingLigne.setCommande(ligneDetails.getCommande());
        }

        validateLigneCommande(existingLigne);

        // Retourner la ligne mise à jour
        return ligneCommandeRepository.save(existingLigne);
    }

    @Transactional
    public void deleteLigneCommande(Long id) {
        if (!ligneCommandeRepository.existsById(id)) {
            throw new NotFoundException("Ligne de commande", id);
        }
        ligneCommandeRepository.deleteById(id);
    }

    public List<LigneCommande> getLignesByProduitId(Long produitId) {
        List<LigneCommande> lignes = ligneCommandeRepository.findByProduitId(produitId);

        // Si aucune ligne de commande n'est trouvée, lancer une exception
        if (lignes.isEmpty()) {
            throw new NotFoundException("Aucune ligne de commande trouvée pour le produit", produitId);
        }

        return lignes;
    }

    public LigneCommande getLigneById(Long id) {
        LigneCommande ligne = ligneCommandeRepository.findByIdWithProduit(id)
                .orElseThrow(() -> new NotFoundException("Ligne de commande", id));

        if (ligne.getProduit() == null) {
            throw new BusinessException("La ligne de commande " + id + " n'a pas de produit associé.");
        }

        return ligne;
    }


    public BigDecimal calculateTotalForLigne(Long ligneCommandeId) {
        return ligneCommandeRepository.calculateTotalForLigne(ligneCommandeId)
                .orElseThrow(() -> new BusinessException("Erreur lors du calcul du total"));
    }
    public LigneCommande createCompleteLigneCommande(Long commandeId, Long produitId, Integer quantite) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new NotFoundException("Commande", commandeId));

        Product produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new NotFoundException("Produit", produitId));

        LigneCommande ligne = new LigneCommande();
        ligne.setCommande(commande);
        ligne.setProduit(produit);
        ligne.setQuantite(quantite);
        ligne.setPrixUnitaire(produit.getPrice()); // Prix actuel du produit

        validateLigneCommande(ligne);
        return ligneCommandeRepository.save(ligne);
    }
    private void validateLigneCommande(LigneCommande ligneCommande) {
        if (ligneCommande.getQuantite() <= 0) {
            throw new BusinessException("La quantité doit être positive");
        }
        if (ligneCommande.getProduit() == null) {
            throw new BusinessException("Un produit doit être associé à la ligne de commande");
        }
        if (ligneCommande.getCommande() == null) {
            throw new BusinessException("Une commande doit être associée à la ligne");
        }
        if (ligneCommande.getPrixUnitaire() == null || ligneCommande.getPrixUnitaire().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Le prix unitaire doit être positif");
        }
    }
}
