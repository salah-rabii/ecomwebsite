package com.myecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clé primaire

    @Column(nullable = false)
    private String name;

//    @Column(nullable = false, unique = true)
//    private String sku;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock; // Quantité en stock

    private String size; // S, M, L, XL, etc.

//    private String color;

//    private String brand;

//    private String material; // Coton, polyester, etc.

    @Column(nullable = false)
    private String gender; // Homme, Femme, Unisexe

    @Column(precision = 5, scale = 2)
    private BigDecimal discount; // Réduction en pourcentage (ex: 10.00 pour 10%)

    private String imageUrl;

    @Column(updatable = false)
    private LocalDateTime createdAt; // Date de création

    private LocalDateTime updatedAt; // Date de mise à jour


    @Column(nullable = false)
    private Integer salesCount=0;




    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    // Méthode utilitaire pour récupérer l'ID de la catégorie
    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }

    // Méthode pour définir la catégorie
    public void setCategory(Category category) {
        if (this.category != null) {
            this.category.getProducts().remove(this);
        }
        this.category = category;
        if (category != null && !category.getProducts().contains(this)) {
            category.getProducts().add(this);
        }
    }
    public void ajouterStock(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        this.stock += quantite;
    }

    public boolean reduireStock(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        if (this.stock < quantite) {
            return false; // Stock insuffisant
        }
        this.stock -= quantite;
        return true;
    }



    public void ajouterVentes(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        this.salesCount += quantite;
    }


    // Met à jour les timestamps
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
