package com.vitapsell.project.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vitapsell.project.model.Listing;
import com.vitapsell.project.model.User;
import com.vitapsell.project.repository.ListingRepository;
import com.vitapsell.project.repository.ListingSpecification;
import com.vitapsell.project.repository.UserRepository;



@RestController
@RequestMapping("/listing")
@CrossOrigin(origins = "http://localhost:5173")
public class ListingController {
    private final ListingRepository lr;
    private final UserRepository ur;
 
    @Value("${app.upload.dir:uploads/images}")
    private String uploadDir;
 
    ListingController(ListingRepository lr, UserRepository ur) {
        this.lr = lr;
        this.ur = ur;
    }
@GetMapping("/getListingAll")
    public List<Listing> get_listingAll() {
        return lr.findAll();
    }
 
    @GetMapping("/getListingByUser")
    public List<Listing> get_listingByUser(@RequestParam int param) {
        User user = ur.findById(param).orElseThrow();
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
    
    @PostMapping(value = "/addlisting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Listing create_listing(
            @RequestPart("listing") Listing entity,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        if (images != null) {
            Path dir = Paths.get(uploadDir);
            Files.createDirectories(dir);
            List<String> filenames = new ArrayList<>();
            for (MultipartFile img : images) {
                String name = UUID.randomUUID() + "_" + img.getOriginalFilename();
                Files.copy(img.getInputStream(), dir.resolve(name), StandardCopyOption.REPLACE_EXISTING);
                filenames.add(name);
            }
            entity.setImageFilenames(filenames);
        }
        return lr.save(entity);
    }
 
      @GetMapping("/filter")
    public List<Listing> filter(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        Specification<Listing> spec = ListingSpecification.withFilters(keyword, type, condition, minPrice, maxPrice);
        return lr.findAll(spec);
    }
 
 
    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws Exception {
        Path file = Paths.get(uploadDir).resolve(filename).normalize();
        Resource resource = new UrlResource(file.toUri());
        if (!resource.exists()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}