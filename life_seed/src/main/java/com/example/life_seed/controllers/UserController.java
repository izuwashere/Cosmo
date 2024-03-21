
package com.example.life_seed.controllers;

import com.example.life_seed.entitys.LoginRequest;
import com.example.life_seed.entitys.RegisterRequest;
import com.example.life_seed.entitys.User;
import com.example.life_seed.entitys.UserResponse;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.services.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/allUser")
@RequiredArgsConstructor
public class UserController {
    
   
     private final UserService userService;
    
    //Create users
    @PostMapping("/login")
        public ResponseEntity <UserResponse> login(@RequestBody LoginRequest request){
            return ResponseEntity.ok(userService.login(request)); 
        }
        
    @PostMapping("/register")
        public  ResponseEntity <UserResponse> register(@RequestBody RegisterRequest request){
            return ResponseEntity.ok(userService.register(request)); 
        }
        
    @GetMapping("/verify")
        public ResponseEntity<UserResponse> verify(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        //System.out.println("hola");
        String token = authorizationHeader.substring(7);
        System.out.println(token); 
        return ResponseEntity.ok(userService.verify(token));

    }
        
        
    //Updated users
  @PutMapping("/updated_user/{id}")
    public ResponseEntity<String> updatedUser(@PathVariable String id, @RequestBody User user) {
        try {
           
            Optional<User> optionalUser = userService.findByIdUser(id);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            
            User existingUser = optionalUser.get();

            
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            existingUser.setPhone(user.getPhone());
            existingUser.setUsername(user.getUsername());
            existingUser.setAddress(user.getAddress());

            
            userService.updatedUser(existingUser);

            
            return ResponseEntity.ok("Usuario actualizado correctamente");
        } catch (Exception ex) {
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario");
        }
    }
    
    
    //Delete users
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
       userService.deleteUser(id);
        return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
    }
    
    
    //List user
       @GetMapping("/listusers")
    public List<User> listUsers(){
        var user = userService.listUser();
        return user;
    }

    
}

       
