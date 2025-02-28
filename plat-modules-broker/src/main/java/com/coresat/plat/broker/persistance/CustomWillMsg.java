package com.coresat.plat.broker.persistance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomWillMsg implements Serializable {
    private String clientId;
    private String topicFilter;
    private int requestedQos;
    private byte[] payload;

}
