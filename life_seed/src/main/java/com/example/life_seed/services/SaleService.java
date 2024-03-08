
package com.example.life_seed.services;

import com.example.life_seed.entitys.Sale;
import com.example.life_seed.entitys.User;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.SaleRepository;
import com.example.life_seed.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {
    
    //Have the repository
    @Autowired
    SaleRepository saleRepository;
    
    @Autowired
    UserRepository userRepository;
    
    //METHOD
    
    //Create sale
     @Transactional
        public void createSale(Sale sale) throws MiException {
    // Validar los campos de la venta
        validateSale(sale);

    // Guardar la venta en la base de datos
        saleRepository.save(sale);
    }

     
    //Updated sale
    public void updatedSale(String saleId, Sale updatedSale) throws MiException {
        // Buscar la venta existente por su ID
        Optional<Sale> optionalSale = saleRepository.findById(saleId);
        if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();
            
            // Actualizar los campos de la venta con los datos proporcionados
            sale.setName(updatedSale.getName());
            sale.setDate(updatedSale.getDate());
            sale.setTotal(updatedSale.getTotal());
            
            // Establecer el usuario de la venta
            sale.setUser(updatedSale.getUser());
            
            // Guardar la venta actualizada en la base de datos
            saleRepository.save(sale);
        } else {
            throw new MiException("Venta no encontrada");
        }
    }

    // Método para validar los campos de la venta
    private void validateSale(Sale sale) throws MiException {
        if (sale == null) {
            throw new MiException("La venta no puede ser nula");
        }
        if (sale.getName() == null || sale.getName().isEmpty()) {
            throw new MiException("El nombre de la venta no puede estar vacío");
        }
        if (sale.getTotal() == null || sale.getTotal() <= 0) {
            throw new MiException("El total de la venta debe ser mayor que cero");
        }
        if (sale.getDate() == null || sale.getDate().isEmpty()) {
            throw new MiException("La fecha de la venta no puede estar vacía");
        }
    }
     
    //Delete sale
    public void deleteSale(String id){
        Optional<Sale> saleToDelete = saleRepository.findById(id);
        if (saleToDelete.isPresent()) {
            saleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Factura no existente.");
        }
    }
     
    //Find id sale
    public Optional<Sale> findByIdSale(String id) {
        return saleRepository.findById(id);
    }
     
    //List sale
    public List<Sale> listSale(){
        return saleRepository.findAll();
    }
        
    //Save sale
    public void saveSale(Sale sale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


