
package com.example.life_seed.controllers;

import com.example.life_seed.entitys.Category;
import com.example.life_seed.entitys.CategoryRequest;
import com.example.life_seed.entitys.ProductRequest;
import com.example.life_seed.entitys.Product;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.ProductRepository;
import com.example.life_seed.services.CategoryService;
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
public class CategoryController {
    
    
    @Autowired
    CategoryService categoryService;
    
    
    
    //Create category
    @PostMapping("/create_category")
    public ResponseEntity<String>createCategory(@RequestBody CategoryRequest request){
       try {
        Category category = new Category();
        
        category.setName(request.getName());
        
        categoryService.createCategory(category);
        
        return new ResponseEntity<>("Categoria creada", HttpStatus.CREATED);
    } catch (ResponseStatusException ex) {
        return new ResponseEntity<>("Error al crear categoria: " + ex.getMessage(), ex.getStatus());
    } catch (MiException ex) {
        return new ResponseEntity<>("Error al crear categoria: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    
    
    //Updated category
    @PutMapping("/updated_category/{id}")
      public ResponseEntity<String> updatedCategory(@PathVariable String id, @RequestBody Category category){
      try {
            categoryService.updatedCategory(id, category);
            return ResponseEntity.ok("Categoria  actualizada correctamente");
        } catch (MiException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la categoria: " + ex.getMessage());
        }
}
      
      
   //Delete category
    @DeleteMapping("/detele_category/{id}")
     public ResponseEntity<String> deleteCategory(@PathVariable String id){
        try{
            categoryService.deleteCategory(id);
        return new ResponseEntity<>("Categoria eliminada", HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>("Error al eliminar la categoria: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //List category
    @GetMapping("/list_category")
     public List<Category> listDetail(){
        var category = categoryService.listCategory();
        return category;
    }
}
