package com.coresat.mqtt.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("mqtt")
public class MqttProperties {
    private String host;
    private List<String> clientIds;
    private String username;
    private String password;
    //    private List<String> topics;
    private int timeout;
    private int keepalive;
    private boolean encrypt;
    private String signCert;
    private String encryptCert;
}
