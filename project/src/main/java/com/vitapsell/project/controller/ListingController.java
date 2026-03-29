package com.vitapsell.project.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitapsell.project.model.User;
import com.vitapsell.project.model.Listing;
import com.vitapsell.project.repository.ListingRepository;
import com.vitapsell.project.repository.UserRepository;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



@RestController
@RequestMapping("/listing")
@CrossOrigin(origins = "http://localhost:5173")
public class ListingController {
    private final ListingRepository lr;
    private final UserRepository ur;
    ListingController(ListingRepository lr,UserRepository ur){
        this.lr=lr;
        this.ur=ur;
    }

    @PostMapping("/addlisting")
    public Listing create_listing(@RequestBody Listing entity) {
        return lr.save(entity);
        
        }
    @GetMapping("/getListingAll") 
    public List<Listing> get_listingAll(){
        return lr.findAll();
    }
    @GetMapping("/getListingByUser") 
    public List<Listing> get_listingByUser(@RequestParam int param){
        User user=ur.findById(param).orElseThrow();
        return lr.findByUser(user);
    }
    @GetMapping("/getListingByType")
    public List<Listing> get_listingByType(@RequestParam String param) {
        return lr.findByType(param);
    }
    @GetMapping("/getListingByCondition")
    public List<Listing> get_listingByCondition(@RequestParam String param) {
        return lr.findByCondition(param);
    }
}
      