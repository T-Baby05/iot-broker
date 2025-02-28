package com.coresat.plat.broker.persistance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomSession implements Serializable {
    private String clientId;
    private String nodeId;
    private boolean cleanSession;
    private int keepAlive;
    private String username;
    private String password;
    private long onlineTime;
}
