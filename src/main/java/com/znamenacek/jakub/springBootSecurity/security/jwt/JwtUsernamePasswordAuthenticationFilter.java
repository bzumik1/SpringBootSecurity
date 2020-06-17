package com.znamenacek.jakub.springBootSecurity.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

/**
 * serves for authentication the user and sending back the token
 */
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration; //contain JWT settings
    private final SecretKey secretKey; //secretKey for signing JWT


    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                   JwtConfiguration jwtConfiguration,
                                                   SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            UsernamePasswordAuthenticationRequest authenticationRequest //what I receive from FE
                    = new ObjectMapper().readValue(request.getInputStream(),UsernamePasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    //sending token if authentication was ok
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
//                .setHeaderParam("typ","JWT") //sets header parameter
                .setSubject(authResult.getName()) //username
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        response.addHeader(jwtConfiguration.getAuthorizationHeader(),jwtConfiguration.getTokenPrefix()+token);

    }
}
