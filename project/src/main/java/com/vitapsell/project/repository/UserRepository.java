package com.vitapsell.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vitapsell.project.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}