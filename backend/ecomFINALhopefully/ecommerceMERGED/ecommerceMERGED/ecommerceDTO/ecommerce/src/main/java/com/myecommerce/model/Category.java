package com.myecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clé primaire auto-générée

    @Column(nullable = false, unique = true)
    private String name; // Nom de la catégorie (unique et obligatoire)

    @Column(length = 500)
    private String description; // Description de la catégorie (facultatif)

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    @Builder.Default
    private List<Product> products = new ArrayList<>(); // Liste des produits liés

    @Column(updatable = false)
    private LocalDateTime createdAt; // Date de création de la catégorie

    private LocalDateTime updatedAt; // Date de dernière modification

    // Méthodes pour gérer la relation bidirectionnelle avec Product
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }

    // Gestion automatique des timestamps
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
