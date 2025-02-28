package com.coresat.plat.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BrokerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(BrokerApplication.class, args);
    }

}
