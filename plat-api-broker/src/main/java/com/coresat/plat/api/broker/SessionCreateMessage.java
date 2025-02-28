package com.coresat.plat.api.broker;

import lombok.Data;

@Data
public class SessionCreateMessage {

    private String clientId;
    private String status;
    private long timestamp;


    public SessionCreateMessage() {
    }

    public SessionCreateMessage(String clientId) {
        this.clientId = clientId;
        this.status="online";
        this.timestamp = System.currentTimeMillis();
    }
}
