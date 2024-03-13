package com.example.life_seed.services;

import com.example.life_seed.entitys.Product;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.CategoryRepository;
import com.example.life_seed.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    //Have the repository
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    //METHOD
    //Create product
    @Transactional
    public void createProduct(Product product) throws MiException {

        valited(product);

        productRepository.save(product);

    }

    //Update products
    public void updatedProduct(String idProduct, Product updatedProduct) throws MiException {
        Optional<Product> respuesta = productRepository.findById(idProduct);

        if (respuesta.isPresent()) {
            Product product = respuesta.get();

            
            if (updatedProduct.getName() != null) {
                product.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                product.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getPrice() != null) {
                product.setPrice(updatedProduct.getPrice());
            }
            if (updatedProduct.getImages() != null) {
                product.setImages(updatedProduct.getImages());
            }

          
            product.setCategory(updatedProduct.getCategory());

     
            productRepository.save(product);
        } else {
            throw new MiException("Producto no encontrado");
        }

    }

    //Delete products
    public void deleteProduct(String id) {
        Optional<Product> productToDelete = productRepository.findById(id);
        if (productToDelete.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("El producto no existe.");
        }
    }

    //List product
    public List<Product> listProduct() {
        return productRepository.findAll();
    }

    //Find id products
    public Optional<Product> findByIdProduct(String id) {
        return productRepository.findById(id);
    }

    //Save product
    public void saveProduct(Product product) {

        throw new UnsupportedOperationException("Producto guardado"); 
    }

    //Valited
    public void valited(Product product) throws MiException {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new MiException("El campo no puede estar nulo o vacio");
        }
        if (product.getPrice() <= 0 || product.getPrice() == null) {
            throw new MiException("mayor a cero");
        }
        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            throw new MiException("El campo no puede estar nulo o vacio");
        }
        if (product == null) {
            throw new MiException("El campo no puede estar nulo o vacio");
        }
    }   
}
