package com.myecommerce.service;

import com.myecommerce.DTO.CategoryDTO;
import com.myecommerce.mapper.CategoryMapper;
import com.myecommerce.model.Category;
import com.myecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // Ajoute une catégorie en la sauvegardant dans la base de données
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    // Met à jour une catégorie en la recherchant via le repository
    public CategoryDTO updateCategory(Long id, CategoryDTO updatedCategoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
        category.setName(updatedCategoryDTO.getName());
        // Mettez à jour d'autres champs si nécessaire

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }



    // Supprime une catégorie en utilisant le repository
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }



    // Recherche de catégories par nom
    public List<CategoryDTO> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }



    // Compte le nombre de produits dans une catégorie
    public long countProductsInCategory(Long categoryId) {
        return categoryRepository.countProductsByCategoryId(categoryId);
    }



    // Vérifie l'existence d'une catégorie par son nom
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }



    // Récupère toutes les catégories depuis la base de données
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }


    // Récupère une catégorie par son ID et retourne un Optional<CategoryDTO>
    public Optional<CategoryDTO> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(categoryMapper::toDto); // Utilisation de Optional pour rendre la réponse cohérente
    }


    // Vérifie si une catégorie existe par son ID
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }


}
