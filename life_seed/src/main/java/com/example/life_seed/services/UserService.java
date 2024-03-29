
package com.example.life_seed.services;

import com.example.life_seed.entitys.LoginRequest;
import com.example.life_seed.entitys.RegisterRequest;
import com.example.life_seed.entitys.Rol;
import com.example.life_seed.entitys.User;
import com.example.life_seed.entitys.UserResponse;
import com.example.life_seed.exception.MiException;
import com.example.life_seed.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    //Have the repository
    
    private final UserRepository userRepository;
    
    private final JwtService jwtService;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;
    
    
    //METHOD
    
    //Create Users
  public UserResponse login(LoginRequest request){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
       UserDetails userDetails = userRepository.findByUsername(request.getUsername()).orElseThrow();
       User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
       String token = jwtService.getToken(userDetails);
       return UserResponse.builder()
               .token(token)
               .user(user)
               .build();
       
  }
    
  
  
  public UserResponse register(RegisterRequest request){
      User user = new User();
              user.setUsername(request.getUsername());
              user.setPassword(passwordEncoder.encode(request.getPassword()));
              user.setName(request.getName());
              user.setPhone(request.getPhone());
              user.setAddress(request.getAddress());
              user.setRol(Rol.USER);

            userRepository.save(user);
            
            return UserResponse.builder()
                    .token(jwtService.getToken(user))
                    .user(user)
                    .build();
  }  
    
    
   
    //Update user
  
    public void updatedUser(User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(updatedUser.getIdUser());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            
            user.setName(updatedUser.getName());
            user.setPhone(updatedUser.getPhone());
            user.setUsername(updatedUser.getUsername());
            user.setAddress(updatedUser.getAddress());

            
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            
            userRepository.save(user);
        } else {
           
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    
    //Delete user
    public void deleteUser(String id){
        Optional<User>userToDelete =  userRepository.findById(id);
        if(userToDelete.isPresent()){
           userRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("El usuario no existe.");
        }
    }
    
    //Find id user
    public Optional<User> findByIdUser(String id) {
        return userRepository.findById(id);
    }
    
    //Find name user
    //public Optional<User> findByName(String name){
        //return userRepository.findByName(name);
    //}
    
    //List user
        public List<User> listUser(){
            return userRepository.findAll();
    }
        
    //Save user
        public void saveUser(User user) {
        
            throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
    //Validate
    public void validate(String name,String phone,String email,String address,String password)  throws MiException{
         if(name == null || name.isEmpty()){
            throw new MiException("El campo no puede estar nulo o vacio");
        }
        
        if(password == null || password.isEmpty()){
            throw new MiException("El campo no puede estar nulo o vacio");
        }
        
        if(email == null || email.isEmpty()){
            throw new MiException("El campo no puede estar nulo o vacio");
        }
        
        if(phone == null || phone.isEmpty()){
            throw new MiException("El campo no puede estar nulo o vacio");
        }
        
        if(address == null || address.isEmpty()){
            throw new MiException("El campo no puede estar nulo o vacio");
        }
    }
    
    
    public UserResponse verify(String token) {
        System.out.println(token);
        String userName = jwtService.getUsernameFromToken(token);
        User user = userRepository.findByUsername(userName).orElseThrow();
        return UserResponse.builder()
                .token(jwtService.getToken(user))
                .user(user)
                .build();
    
}
}
