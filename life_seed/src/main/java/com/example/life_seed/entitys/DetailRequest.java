
package com.example.life_seed.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailRequest {
    private Double amount;
    private String idSale;
    private String idProduct;
    
}
