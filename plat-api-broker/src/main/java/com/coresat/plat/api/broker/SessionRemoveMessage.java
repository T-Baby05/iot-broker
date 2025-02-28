package com.coresat.plat.api.broker;

import lombok.Data;

@Data
public class SessionRemoveMessage {

    private String clientId;
    private String status;
    private long timestamp;


    public SessionRemoveMessage() {
    }

    public SessionRemoveMessage(String clientId) {
        this.clientId = clientId;
        this.status = "offline";
        this.timestamp = System.currentTimeMillis();
    }
}
