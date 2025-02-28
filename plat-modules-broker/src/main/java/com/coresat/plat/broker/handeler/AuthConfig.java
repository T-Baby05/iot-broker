package com.coresat.plat.broker.handeler;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "moquette.auth")
public class AuthConfig {

    private String type;
    private String httpUrl;
    private int httpTimeout;

}