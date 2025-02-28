package com.coresat.mqtt.client;

import com.alibaba.fastjson.JSON;
import com.coresat.runner.PassObjectVo;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.List;


/**
 * MqttCustomerClient
 *
 * @author Created by wangzaihong on 2024/08/09
 */
@Slf4j
public class MqttCustomerClient {
    private final MqttProperties mqttProperties;
    private MqttClient client;

    public MqttCustomerClient(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    public void initializeMqttClient(String clientId) {
        try {
            client = new MqttClient(mqttProperties.getHost(), clientId, new MemoryPersistence());
            client.setCallback(new MessageCallback(client));
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 客户端连接
     */
    public void connect() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(mqttProperties.getUsername());
//            byte[] encodeSign = Base64.getEncoder().encode(mqttProperties.getSignCert().getBytes(StandardCharsets.UTF_8));
//            byte[] encodeEncrypt = Base64.getEncoder().encode(mqttProperties.getEncryptCert().getBytes(StandardCharsets.UTF_8));
//            String s = calculatePassword(mqttProperties.getPassword(), new String(encodeSign), new String(encodeEncrypt));
            options.setPassword(mqttProperties.getPassword().toCharArray());
            options.setConnectionTimeout(mqttProperties.getTimeout());
            options.setAutomaticReconnect(true);
            options.setKeepAliveInterval(mqttProperties.getKeepalive());

//            options.setWill("Hello","hello will".getBytes(StandardCharsets.UTF_8),0,false);
            client.connect(options);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

    }

    private String calculatePassword(String password, String signCert, String encryptCert) {

        PassObjectVo passObjectVo = new PassObjectVo(password, signCert, encryptCert);
        return JSON.toJSONString(passObjectVo);
    }


    /**
     * 发布，默认qos为0，非持久化
     *
     * @param topic       topic
     * @param pushMessage pushMessage
     */
    public void publish(String topic, String pushMessage) {
        publish(0, false, topic, pushMessage);
    }

    /**
     * 发布
     *
     * @param qos         连接方式
     * @param retained    是否保留
     * @param topic       主题
     * @param pushMessage 消息体
     */
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mqttTopic = client.getTopic(topic);
        if (null == mqttTopic) {
            log.error("cannot find topic by name :{}", topic);
            throw new RuntimeException();
        }
        MqttDeliveryToken token;
        try {
            token = mqttTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    /**
     * 订阅某个主题，qos默认为0
     */
    public void subscribe(List<String> topics) {
        topics.forEach(topic -> {
            subscribe(topic, 1);
        });
    }

    private void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
