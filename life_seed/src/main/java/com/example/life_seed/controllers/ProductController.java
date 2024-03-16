package com.example.life_seed.controllers;

import com.example.life_seed.entitys.Category;
import com.example.life_seed.entitys.Product;
import com.example.life_seed.entitys.ProductRequest;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.CategoryRepository;
import com.example.life_seed.services.ProductService;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/allProduct")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Autowired
    CategoryRepository categoryRepository;

    //Create product
    @PostMapping("/create_product")
    public ResponseEntity<String> createProduct(@RequestParam("name") String name,
            @RequestParam("idCategory") String idCategory,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("images") MultipartFile images) {
        try {
            Category category = categoryRepository.findById(idCategory).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));

            Product product = new Product();
            byte[] fileBytes = images.getBytes();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(category);
            product.setImages(fileBytes);

            productService.createProduct(product);
            return new ResponseEntity<>("Product creado", HttpStatus.CREATED);
        } catch (IOException ex) {
            return new ResponseEntity<>("Error al crear la producto: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al crear producto: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Updated products
    @PutMapping("/updated_product/{id}")
    public ResponseEntity<String> updatedProduct(@PathVariable String id, @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "idCategory") String idCategory,
            @RequestParam(required = false, name = "price") Double price,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "images") MultipartFile images) throws IOException {
        try {
            Product product = new Product();
            if (name != null) {
                product.setName(name);
            }
            if (description != null) {
                product.setDescription(description);
            }
            if (price != null) {
                product.setPrice(price);
            }
            if (idCategory != null) {
                Category category = categoryRepository.findById(idCategory).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));
                product.setCategory(category);
            }
            if (images != null) {
                byte[] fileBytes = images.getBytes();
                product.setImages(fileBytes);
            }
            productService.updatedProduct(id, product);

            return ResponseEntity.ok("Producto actualizado correctamente");
        } catch (MiException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el producto: " + ex.getMessage());
        }
    }

    //Lisy products
    @GetMapping("/listproducts")
    public List<Product> listProducts() {
        var product = productService.listProduct();
        return product;
    }

    //Delete products
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
    }
}
