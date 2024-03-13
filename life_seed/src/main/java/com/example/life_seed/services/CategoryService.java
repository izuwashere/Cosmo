
package com.example.life_seed.services;

import com.example.life_seed.entitys.Category;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    
    //Have the repository
    @Autowired
    CategoryRepository categoryRepository;
    
    
    
    //METHOD
    //Create detail
    @Transactional
    public void createCategory(Category category) throws MiException{
        
      valited(category);
   
      categoryRepository.save(category);
     
}
    
    //Updated category
    public void updatedCategory(String CategoryId,Category updatedCategory) throws MiException{
     
        Optional<Category> respuesta = categoryRepository.findById(CategoryId);
        if (respuesta.isPresent()) {
            Category category = respuesta.get();
            
         
            category.setName(updatedCategory.getName());
            
            
            categoryRepository.save(category);
        } else {
            throw new MiException("Categoria no encontrada");
        }
}
    
    //Delete category
    public void deleteCategory(String id){
        Optional<Category>categoryToDelete = categoryRepository.findById(id);
        
            if(categoryToDelete.isPresent()){
            categoryRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Categoria no existente.");
    }    
}
    
    //List category
    public List<Category>listCategory(){
        return categoryRepository.findAll();
    }
    
    public Optional<Category>findByIdCategory(String id){
            return categoryRepository.findById(id);
        }
    
    //Save category
    public void saveCategory(Category category){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //Valited detail
        public void valited(Category category) throws MiException{
            if(category.getName() == null || category.getName().isEmpty()){
                throw new MiException("Debe estar lleno este campo");
           }
            if(category == null){
                throw new MiException("Debe estar lleno este campo");
            }
        }
}
