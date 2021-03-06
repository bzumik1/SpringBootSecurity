package com.znamenacek.jakub.springBootSecurity.security;

import com.znamenacek.jakub.springBootSecurity.security.auth.ApplicationUserService;
import com.znamenacek.jakub.springBootSecurity.security.jwt.JwtConfiguration;
import com.znamenacek.jakub.springBootSecurity.security.jwt.JwtTokenVerifierFilter;
import com.znamenacek.jakub.springBootSecurity.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.znamenacek.jakub.springBootSecurity.security.auth.enums.ApplicationUserRole.*;

@Configuration @EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder,
                                            ApplicationUserService applicationUserService,
                                            JwtConfiguration jwtConfiguration,
                                            SecretKey secretKey){
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfiguration = jwtConfiguration;
        this.secretKey = secretKey;
    }

    @Override //SETTINGS FOR BASIC AUTHENTICATION
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// disables csrf (cross side request forgery)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //because of sessions
                    .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfiguration, secretKey)) // because of JWT for sending token
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfiguration, secretKey), JwtUsernamePasswordAuthenticationFilter.class)
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
                .authenticated(); //authenticated
//                .and() // and
//                .httpBasic(); //basic authentication
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder); //allows passwords to be decoded
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    //    @Override //SETTINGS FOR USERS
//    @Bean
//    protected UserDetailsService userDetailsService() {
//
//        UserDetails admin = User.builder() //creates new user
//                .username("Jakub")
//                .password(passwordEncoder.encode("1234")) //must be encoded
//                .roles(ADMIN.name()) // ROLE_ADMIN
////                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails lukasUser = User.builder() //creates new user
//                .username("Lukas")
//                .password(passwordEncoder.encode("lukas"))
//                .roles(STUDENT.name())
////                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//
//        UserDetails petrUser= User.builder()
//                .username("Petr")
//                .password(passwordEncoder.encode("petr"))
////                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//
//        return  new InMemoryUserDetailsManager(
//                admin,
//                petrUser,
//                lukasUser
//        );
//    }
}
