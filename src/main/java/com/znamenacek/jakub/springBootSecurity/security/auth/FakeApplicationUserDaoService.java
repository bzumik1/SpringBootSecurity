package com.znamenacek.jakub.springBootSecurity.security.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.znamenacek.jakub.springBootSecurity.security.auth.enums.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDAO {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> findApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> applicationUser.getUsername().equals(username))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        return Lists.newArrayList(
           new ApplicationUser(
                   "Jakub",
                   passwordEncoder.encode("1234"),
                   ADMIN.getGrantedAuthorities(),
                   true,
                   true,
                   true,
                   true

           ),
           new ApplicationUser(
                   "Lukas",
                    passwordEncoder.encode("lukas"),
                    STUDENT.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true

           ),
           new ApplicationUser(
                    "Petr",
                    passwordEncoder.encode("petr"),
                    ADMINTRAINEE.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true

           )
        );
    }
}
