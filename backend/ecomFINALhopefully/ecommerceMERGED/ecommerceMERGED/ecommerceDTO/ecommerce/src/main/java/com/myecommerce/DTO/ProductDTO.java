package com.myecommerce.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    //private String sku;
    private String description;
    private BigDecimal price;
    private int stock;
    private String size;
    private Integer salesCount;

    // private String color;
    //Qprivate String brand;
    //private String material;
    private String gender;
    private BigDecimal discount;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryId; // Référence à la catégorie sans charger l'objet Category
}