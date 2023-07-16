package com.made.api_jwt.repository;

import com.made.api_jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);

}
