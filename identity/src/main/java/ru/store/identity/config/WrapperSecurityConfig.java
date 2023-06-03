package ru.store.identity.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
@EnableWebSecurity
public class WrapperSecurityConfig {
    @Value("${jwt.signKey}")
    String signKey;
    @Value("${jwt.expiration}")
    Long expiration;
}
