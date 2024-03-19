
package com.example.life_seed.entitys;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Detail {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid" , strategy = "uuid2")
    private String idDeatil;
    
    private Double amount;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale")
    private Sale sale;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product")
    private Product product;
    
}
