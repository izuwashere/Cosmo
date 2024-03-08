
package com.example.life_seed.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequest {
    private String name;
    private double total;
    private String date;
    private String idUser;
    
}

