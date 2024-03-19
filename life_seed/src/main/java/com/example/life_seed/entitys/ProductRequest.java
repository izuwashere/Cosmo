
package com.example.life_seed.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    
    private String idProduct;
    private String name;
    private String idCategory;
    private double price;
    private String desciption;
    private MultipartFile images;
}
