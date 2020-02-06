package com.znamenacek.jakub.springBootSecurity.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * this class serves for verifying the token, token is verified only once per request
 */
public class JwtTokenVerifierFilter extends OncePerRequestFilter {
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    public JwtTokenVerifierFilter(JwtConfiguration jwtConfiguration, SecretKey secretKey) {
        this.jwtConfiguration = jwtConfiguration;
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //gets token from header
        String authorizationHeader = request.getHeader(jwtConfiguration.getAuthorizationHeader());

        //checks if the token is in header
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfiguration.getTokenPrefix())){
            filterChain.doFilter(request,response);
            return;
        }

        //if so extracts pure token
        String token = authorizationHeader.replace(jwtConfiguration.getTokenPrefix(),"");
        try{



            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token); //Jws = signed Jwt

            Claims body = claimsJws.getBody();

            String username = body.getSubject(); // username
            var authorities = (List<Map<String,String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );
            //Authenticates user (sets authentication true)
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e){
            throw new IllegalStateException("Token can not be trusted.");
        }

        //sends request and respond to next filter or api
        filterChain.doFilter(request,response);

    }
}
