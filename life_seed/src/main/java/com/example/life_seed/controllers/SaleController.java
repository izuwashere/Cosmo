
package com.example.life_seed.controllers;

import com.example.life_seed.entitys.Sale;
import com.example.life_seed.entitys.User;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.UserRepository;
import com.example.life_seed.services.SaleService;
import java.util.List;

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
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/allUser")
@RequiredArgsConstructor
public class SaleController {
    
    private final SaleService saleService;
    
    @Autowired
    private UserRepository userRepository;

    
@PostMapping("/create_sale")
public ResponseEntity<String> createSale(@RequestBody SaleRequest request) {
    try {
        String userId = request.getIdUser();
        
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        
        Sale sale = new Sale();
        sale.setName(request.getName());
        sale.setTotal(request.getTotal());
        sale.setDate(request.getDate());
        sale.setUser(user);
        
        saleService.createSale(sale);
        
        return new ResponseEntity<>("Factura creada", HttpStatus.CREATED);
    } catch (ResponseStatusException ex) {
        return new ResponseEntity<>("Error al crear factura: " + ex.getMessage(), ex.getStatus());
    } catch (MiException ex) {
        return new ResponseEntity<>("Error al crear factura: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    
    // Updated sale
    @PutMapping("/updated_sale/{id}")
    public ResponseEntity<String> updatedSale(@PathVariable String id, @RequestBody Sale sale) {
        try {
            saleService.updatedSale(id, sale);
            return ResponseEntity.ok("Factura actualizada correctamente");
        } catch (MiException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la factura: " + ex.getMessage());
        }
    }
    
    // Delete sale
    @DeleteMapping("/delete_sale/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable String id) {
        try {
            saleService.deleteSale(id);
            return new ResponseEntity<>("Factura eliminada", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error al eliminar factura: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // List sale
    @GetMapping("/list_sales")
    public List<Sale> listSale() {
        return saleService.listSale();
    }
}
