package com.vitapsell.project.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitapsell.project.model.User;
import com.vitapsell.project.repository.UserRepository;
import com.vitapsell.project.service.Hashing;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository ur;
    private final Hashing hash;
    UserController(UserRepository ur,Hashing hash){
        this.ur=ur;
        this.hash=hash;
    }
    @PostMapping("/register")
    public User create_user(@RequestBody User entity) {
        String hashed_pass=hash.hashPassword(entity.getPassword());
        entity.setPassword(hashed_pass);
       
        
        return ur.save(entity);
    }
    @PostMapping("/login")
    public String postMethodName(@RequestBody User entity) {
        User user=ur.findByEmail(entity.getEmail());
        if(user==null){
            throw new RuntimeException("email not found");
        }
        if(!(hash.verifyPassword(entity.getPassword(),user.getPassword()))){
            throw new RuntimeException("Invalid credentials");
        }
        else{
            return "login sucessful";
        }
        
    }
    
    
    
}
