
package com.example.life_seed.services;

import com.example.life_seed.entitys.Detail;
import com.example.life_seed.entitys.Product;
import com.example.life_seed.entitys.Sale;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.DetailRepository;
import com.example.life_seed.repositories.ProductRepository;
import com.example.life_seed.repositories.SaleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetailService {
    
    
     //Have the repository
    @Autowired
    DetailRepository detailRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    SaleRepository saleRepository;
    
    
     //METHOD
    
    //Create detail
    @Transactional
    public void createDetail(Detail detail)throws MiException {
        
        valited(detail);
        
    
        detailRepository.save(detail);
   
    }
    
    //Update detail
        public void updatedDetail(String idDetail ,Detail updatedDetail) throws MiException{
            Optional<Detail>respuesta = detailRepository.findById(idDetail);
            
            if(respuesta.isPresent()){
                Detail detail = respuesta.get();
            
                if(updatedDetail.getAmount() != null){
                    detail.setAmount(updatedDetail.getAmount());
                }
                if(updatedDetail.getProduct() != null){
                     detail.setProduct(updatedDetail.getProduct());
                }
                if(updatedDetail.getSale() != null){
                    detail.setSale(updatedDetail.getSale());
                }
                detailRepository.save(detail);
                
            }else {
                throw new MiException("Detalle no encontrado");
            }
          
    }
    
    //Delete detail
        public void deleteDetail(String id){
            Optional<Detail>detailToDelete = detailRepository.findById(id);
            if(detailToDelete.isPresent()){
                detailRepository.deleteById(id);
            }else{
                throw new IllegalArgumentException("Detalle no existente.");
            }
        }
        
     //Find detail id
        public Optional<Detail>findByIdDetail(String id){
            return detailRepository.findById(id);
        }
        
     //List detail
        public List<Detail> listDetail(){
            return detailRepository.findAll();
    }
        
     //Save detail
        public void saveSale(Detail detail) {
        
            throw new UnsupportedOperationException("Not supported yet."); 
    }
        
    //Valited detail
        public void valited(Detail detail) throws MiException{
            if(detail == null){
                throw new MiException("Debe estar lleno este campo");
           }
             if(detail.getAmount() <= 0 || detail.getAmount() == null){
                throw new MiException("Debe ser mayor a cero");
           }
             if(detail.getProduct() == null){
                throw new MiException("Debe estar lleno este campo");
           }
             if(detail.getSale() == null){
                 throw new MiException("Debe estar lleno este campo");
             }
        }
}
