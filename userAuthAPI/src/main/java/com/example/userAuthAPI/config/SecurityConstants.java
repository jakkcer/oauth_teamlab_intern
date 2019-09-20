package com.example.userAuthAPI.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "constant.security")
public class SecurityConstants {
	private String secret;
	private long expirationTime;
	private String tokenPrefix;
    private String headerString;
}