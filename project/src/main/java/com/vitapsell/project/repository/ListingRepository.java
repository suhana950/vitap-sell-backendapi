package com.vitapsell.project.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vitapsell.project.model.Listing;
import com.vitapsell.project.model.User;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Integer> {
    List<Listing> findByUser(User user);
    List<Listing> findByType(String type);
    List<Listing> findByCondition(String condition);
    List<Listing> findAll(Specification<Listing> spec);

   
}