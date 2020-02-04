package com.znamenacek.jakub.springBootSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import static com.znamenacek.jakub.springBootSecurity.security.ApplicationUserRole.*;
import static com.znamenacek.jakub.springBootSecurity.security.ApplicationUserPermission.*;

@Configuration @EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfing extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfing(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override //SETTINGS FOR BASIC AUTHENTICATION
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// disables csrf
                .authorizeRequests() //authorize requests

                .antMatchers("/","index","/css/*","/js/*")  //for these no authentication will be necessary
                    .permitAll() // the patterns above will be accessible without authentication

                .antMatchers("/api/**") //these can be accessed only by students
                    .hasRole(STUDENT.name()) //role which can access url above

//                .antMatchers(HttpMethod.DELETE, "/management/api/**")
//                    .hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/management/api/**")
//                    .hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/api/**")
//                    .hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"management/api/**")
//                    .hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())

                .anyRequest() // any request has to be
                .authenticated() //authenticated
                .and() // and
                .httpBasic(); //basic authentication
    }

    @Override //SETTINGS FOR USERS
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails admin = User.builder() //creates new user
                .username("Jakub")
                .password(passwordEncoder.encode("1234")) //must be encoded
                .roles(ADMIN.name()) // ROLE_ADMIN
//                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails lukasUser = User.builder() //creates new user
                .username("Lukas")
                .password(passwordEncoder.encode("lukas"))
                .roles(STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails petrUser= User.builder()
                .username("Petr")
                .password(passwordEncoder.encode("petr"))
//                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return  new InMemoryUserDetailsManager(
                admin,
                petrUser,
                lukasUser
        );
    }
}
