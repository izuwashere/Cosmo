
package com.example.life_seed.repositories;

import com.example.life_seed.entitys.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<Detail,String> {
    
}
