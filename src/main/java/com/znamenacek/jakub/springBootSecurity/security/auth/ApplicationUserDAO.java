package com.znamenacek.jakub.springBootSecurity.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ApplicationUserDAO{
    Optional<ApplicationUser> findApplicationUserByUsername(String username);
}
