
package com.example.life_seed.controllers;

import com.example.life_seed.entitys.Detail;
import com.example.life_seed.entitys.DetailRequest;
import com.example.life_seed.entitys.Product;
import com.example.life_seed.entitys.Sale;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.ProductRepository;
import com.example.life_seed.repositories.SaleRepository;
import com.example.life_seed.services.DetailService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/allUser")
@RequiredArgsConstructor
public class DetailController {
    
    
     private final  DetailService detailService;
     
     @Autowired
     SaleRepository saleRepository;
     
     @Autowired 
     ProductRepository productRepository;
     
     //Create detail
     @PostMapping("/create_detail")
     public ResponseEntity<String>createDetail(@RequestBody DetailRequest request){
        try{
            String idSale = request.getIdSale();
            String idProduct = request.getIdProduct();
            
            Sale sale = saleRepository.findById(idSale).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada"));
            Product product = productRepository.findById(idProduct).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
            
            Detail detail = new Detail();
            
            detail.setAmount(request.getAmount());
            detail.setProduct(product);
            detail.setSale(sale);
            
            detailService.createDetail(detail);
            
            return new ResponseEntity<>("Detalle de factura creado", HttpStatus.CREATED);
            
       } catch (ResponseStatusException ex) {
        return new ResponseEntity<>("Error al crear el detalle de la factura: " + ex.getMessage(), ex.getStatus());
    } catch (MiException ex) {
        return new ResponseEntity<>("Error al crear el detalle de la factura: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
     
         //Updated detail
     @PutMapping("/updated_detail/{id}")
        public ResponseEntity<String> updatedDetail(@PathVariable String id, @RequestBody Detail detail) {
            try{
                detailService.updatedDetail(id, detail);
                 return ResponseEntity.ok("Detalle de factura actualizada correctamente");
            }catch (MiException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el detalle: " + ex.getMessage());
        }
    }
        
    //Delete sale
    @DeleteMapping("/details/{id}")
    public ResponseEntity<String> deleteDetail(@PathVariable String id){
        try{
            detailService.deleteDetail(id);
        return new ResponseEntity<>("Detalle eliminada", HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>("Error al eliminar el detalle: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
        //List sale
    @GetMapping("/listdetails")
    public List<Detail> listDetail(){
        var detail = detailService.listDetail();
        return detail;
    }
}
