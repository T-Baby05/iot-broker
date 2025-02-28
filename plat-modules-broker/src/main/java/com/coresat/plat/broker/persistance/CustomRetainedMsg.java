package com.coresat.plat.broker.persistance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomRetainedMsg implements Serializable {
    private String topic;
    private int qos;
    private byte[] payload;

}
