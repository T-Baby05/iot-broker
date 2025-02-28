package com.coresat.mqtt.client;

import com.coresat.MqttClientApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MqttClientApplication.class)
public class MqttCustomerClientTest {
    @Autowired
    private MqttCustomerClient mqttCustomerClient;

    @Test
    void publish() {
        for (int i = 0; i < 10; i++) {
            mqttCustomerClient.publish("test/device1", "hello mqtt............" + i);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
