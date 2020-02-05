package com.znamenacek.jakub.springBootSecurity.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}
