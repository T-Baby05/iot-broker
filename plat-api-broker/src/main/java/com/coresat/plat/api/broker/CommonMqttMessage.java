package com.coresat.plat.api.broker;

public class CommonMqttMessage {

    private String clientId;
    private String topic;
    private int qos;
    private String payload;


    public CommonMqttMessage() {
    }

    public CommonMqttMessage(String clientId, String topic, int qos, String payload) {
        this.clientId = clientId;
        this.topic = topic;
        this.qos = qos;
        this.payload = payload;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
