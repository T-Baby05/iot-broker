package com.coresat.runner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassObjectVo {

    private String pass;
    private String signCert;
    private String encryptCert;
}
