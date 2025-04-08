package com.myecommerce.service;

import com.myecommerce.model.*;
import com.myecommerce.exception.CommandeException;
import com.myecommerce.exception.ProductException;
import com.myecommerce.exception.StockException;
import com.myecommerce.repository.CommandeRepository;
import com.myecommerce.repository.LigneCommandeRepository;
import com.myecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProductRepository produitRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final UserService utilisateurService;

    @Autowired
    public CommandeService(CommandeRepository commandeRepository,
                           ProductRepository produitRepository,
                           LigneCommandeRepository ligneCommandeRepository,
                           UserService utilisateurService) {
        this.commandeRepository = commandeRepository;
        this.produitRepository = produitRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.utilisateurService = utilisateurService;
    }

    public Commande creerPanier(Long utilisateurId) {
        AppUser utilisateur = utilisateurService.trouverParId(utilisateurId);

        if (utilisateur == null) {
            throw new CommandeException("Utilisateur non trouvé avec l'ID: " + utilisateurId);
        }

        Commande panier = new Commande();
        panier.setUtilisateur(utilisateur);
        panier.setStatut(StatutCommande.PANIER);
        panier.setDate(LocalDateTime.now());

        return commandeRepository.save(panier);
    }
    @Transactional//il faut ajouter le test pour le statut et la quantité
    public Commande ajouterProduitAuPanier(Long commandeId, Long produitId, int quantite) {
        try {
            if (quantite <= 0) {
                throw new IllegalArgumentException("La quantité doit être positive");
            }
            Commande commande = getCommandeEnPanier(commandeId);
            if (commande == null || !commande.getStatut().equals(StatutCommande.PANIER)) {
                throw new CommandeException("Panier non trouvé");
            }
            Product produit = produitRepository.findById(produitId)
                    .orElseThrow(() -> new CommandeException("Produit non trouvé"));
            if (produit.getStock() == null || produit.getStock() < quantite) {
                throw new StockException("Stock insuffisant: " + produit.getName());
            }
            if (produit.getPrice() == null) {
                throw new ProductException("Prix non défini pour: " + produit.getName());
            }
            Optional<LigneCommande> ligneExistante = commande.getLigneCommandes().stream()
                    .filter(l -> l.getProduit().getId().equals(produitId))
                    .findFirst();

            if (ligneExistante.isPresent()) {
                LigneCommande ligne = ligneExistante.get();
                ligne.setQuantite(ligne.getQuantite() + quantite);
            } else {
                LigneCommande nouvelleLigne = new LigneCommande();
                nouvelleLigne.setCommande(commande);
                nouvelleLigne.setProduit(produit);
                nouvelleLigne.setQuantite(quantite);
                nouvelleLigne.setPrixUnitaire(produit.getPrice());
                commande.getLigneCommandes().add(nouvelleLigne);
            }

            commande.calculerTotal();
            return commandeRepository.save(commande);

        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du produit au panier: " + e.getMessage());
            throw e;
        }
    }

    public Commande passerCommande(Long commandeId) {   //le test de statut
        Commande commande = getCommandeEnPanier(commandeId);

        if (commande.getLigneCommandes().isEmpty()) {
            throw new CommandeException("Impossible de passer une commande vide");
        }

        commande.passerCommande();
        return commandeRepository.save(commande);
    }

    public Commande annulerCommande(Long commandeId) {
        Commande commande = trouverCommande(commandeId);

        if (commande.getStatut() == StatutCommande.LIVREE) {
            throw new CommandeException("Impossible d'annuler une commande déjà livrée");
        }

        commande.annulerCommande();
        return commandeRepository.save(commande);
    }


    public Commande trouverCommande(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeException("Commande non trouvée avec l'id: " + id));
    }


    public List<Commande> getCommandesUtilisateur(Long utilisateurId) {
        if (utilisateurId == null || utilisateurId <= 0) {
            throw new IllegalArgumentException("ID utilisateur invalide");
        }

        // Vérification de l'existence de l'utilisateur
        AppUser utilisateur = Optional.ofNullable(utilisateurService.trouverParId(utilisateurId))
                .orElseThrow(() -> new CommandeException("Utilisateur ID " + utilisateurId + " non trouvé"));

        // Récupère toutes les commandes de l'utilisateur, y compris celles avec le statut "PANIER"
        List<Commande> commandes = commandeRepository.findByUtilisateurId(utilisateurId);

        // Vous pouvez éventuellement traiter les commandes ici si besoin, mais le filtre est déjà large
        return commandes;
    }




    public Commande mettreAJourStatut(Long commandeId, StatutCommande nouveauStatut) {
        Commande commande = trouverCommande(commandeId);

        if (commande.getStatut() == StatutCommande.ANNULEE) {
            throw new CommandeException("Impossible de modifier une commande annulée");
        }

        commande.setStatut(nouveauStatut);

        // 🔥 Mise à jour des ventes si la commande est livrée
        if (commande.getStatut() == StatutCommande.LIVREE) {
            for (LigneCommande ligne : commande.getLigneCommandes()) {
                Product produit = ligne.getProduit();
                produit.ajouterVentes(ligne.getQuantite()); // Ajout des ventes
                produitRepository.save(produit); // Sauvegarde du produit mis à jour
            }
        }

        return commandeRepository.save(commande);
    }


    public Commande getCommandeEnPanier(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new CommandeException("Commande non trouvée"));

        if (!commande.getStatut().equals(StatutCommande.PANIER)) {
            throw new CommandeException("Seuls les paniers peuvent être modifiés");
        }
        return commande;
    }

}