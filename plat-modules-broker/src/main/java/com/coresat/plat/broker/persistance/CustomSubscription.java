package com.coresat.plat.broker.persistance;

import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomSubscription implements Serializable {
    private String clientId;
    private String topicFilter;
    private MqttQoS requestedQos;

}
