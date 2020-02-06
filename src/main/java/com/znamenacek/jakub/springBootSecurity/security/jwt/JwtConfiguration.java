package com.znamenacek.jakub.springBootSecurity.security.jwt;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@NoArgsConstructor @Getter @Setter
//@Component //could be problem
//@Configuration // for bean
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfiguration {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;



    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }

}
