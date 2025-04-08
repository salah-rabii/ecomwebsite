package com.myecommerce.controller;

import com.myecommerce.DTO.ProductDTO;
import com.myecommerce.model.Product;
import com.myecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Ajouter un produit
    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ProductDTO addedProduct = productService.addProduct(productDTO);
        return ResponseEntity.status(201).body(addedProduct);
    }


    // Endpoint pour récupérer les produits par sexe
    @GetMapping("/gender/{gender}")
    public List<Product> getProductsByGender(@PathVariable String gender) {
        return productService.getProductsByGender(gender);
    }

    // Modifier un produit
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO updatedProductDTO) {
        Optional<ProductDTO> existingProduct = Optional.ofNullable(productService.getProductById(id));
        if (existingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProductDTO updated = productService.updateProduct(id, updatedProductDTO);
        return ResponseEntity.ok(updated);
    }


    // Supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    // Récupérer un produit par ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> product = Optional.ofNullable(productService.getProductById(id));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Mettre à jour le stock d'un produit
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestParam int newStock) {
        if (!productService.existsById(id) || newStock < 0) {
            return ResponseEntity.badRequest().build();
        }
        productService.updateStock(id, newStock);
        return ResponseEntity.ok().build();
    }


    // Récupérer tous les produits avec pagination
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }


    // Rechercher des produits par nom
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProductsByName(name));
    }


    // Compter le nombre de produits dans une catégorie
    @GetMapping("/count-by-category/{categoryId}")
    public ResponseEntity<Long> countProductsByCategory(@PathVariable Long categoryId) {
        if (!productService.existsCategoryById(categoryId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productService.countProductsInCategory(categoryId));
    }
}
