
package com.example.life_seed.repositories;

import com.example.life_seed.entitys.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,String> {
    
}
