package com.myecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestStripe {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;


}


