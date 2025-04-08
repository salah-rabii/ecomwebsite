package com.myecommerce.service;

import com.myecommerce.DTO.ProductDTO;
import com.myecommerce.mapper.ProductMapper;
import com.myecommerce.model.Product;
import com.myecommerce.repository.CategoryRepository;
import com.myecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository; // Injection du repository des catégories



    // Ajoute un produit en le sauvegardant dans la base de données
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO); // Mapper DTO vers Entity
        product.setSalesCount(0);

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct); // Mapper Entity vers DTO
    }

    // Met à jour un produit via le repository
    public ProductDTO updateProduct(Long id, ProductDTO updatedProductDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        // Met à jour les informations du produit
        // Mise à jour de tous les attributs du produit
        existingProduct.setName(updatedProductDTO.getName());
        existingProduct.setDescription(updatedProductDTO.getDescription());
        existingProduct.setPrice(updatedProductDTO.getPrice());
        existingProduct.setStock(updatedProductDTO.getStock());
        existingProduct.setSize(updatedProductDTO.getSize());  // Ajouter la taille
        existingProduct.setGender(updatedProductDTO.getGender());  // Ajouter le genre
        existingProduct.setDiscount(updatedProductDTO.getDiscount());  // Mettre à jour le discount
        existingProduct.setImageUrl(updatedProductDTO.getImageUrl());  // Mettre à jour l'URL de l'image

        // Si nécessaire, tu peux mettre à jour la date de modification
        existingProduct.setUpdatedAt(LocalDateTime.now());

        // Sauvegarde les changements
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    // Supprime un produit via le repository
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    // Récupère un produit par son ID
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return productMapper.toDto(product);
    }




    // Met à jour le stock d'un produit
    public void updateStock(Long productId, int newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        product.setStock(newStock);
        productRepository.save(product);
        System.out.println("Le stock a été mis à jour à : " + newStock);
    }



    // Récupère tous les produits avec pagination
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }


    // Méthode pour obtenir les produits filtrés par sexe
    public List<Product> getProductsByGender(String gender) {
        return productRepository.findByGenderIgnoreCase(gender);
    }


    // Recherche des produits par nom
    public List<ProductDTO> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    // Compte le nombre de produits par catégorie
    public long countProductsInCategory(Long categoryId) {
        return productRepository.countByCategory_Id(categoryId);
    }

    // Méthode pour vérifier si un produit existe par son ID
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

//vérifie si une catégorie existe dans la base de données en fonction de son id.
    public boolean existsCategoryById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
