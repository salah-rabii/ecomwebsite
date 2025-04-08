package com.myecommerce.controller;

import com.myecommerce.DTO.CategoryDTO;
import com.myecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Récupérer toutes les catégories (avec DTO)
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Récupérer une catégorie par ID (avec DTO)
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(id);
        return categoryDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter une catégorie (avec DTO)
    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        CategoryDTO addedCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(201).body(addedCategory);
    }

    // Modifier une catégorie (avec DTO)
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO updatedCategoryDTO) {
        Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(id);
        if (categoryDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CategoryDTO updatedCategory = categoryService.updateCategory(id, updatedCategoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    // Supprimer une catégorie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Rechercher des catégories par nom (avec DTO)
    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategoriesByName(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.searchCategoriesByName(name));
    }

    // Vérifier si une catégorie existe par son nom
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByName(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.existsByName(name));
    }

    // Compter le nombre de produits dans une catégorie
    @GetMapping("/count-products/{categoryId}")
    public ResponseEntity<Long> countProductsInCategory(@PathVariable Long categoryId) {
        if (!categoryService.existsById(categoryId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryService.countProductsInCategory(categoryId));
    }
}
