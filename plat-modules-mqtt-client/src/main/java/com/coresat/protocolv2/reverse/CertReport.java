package com.coresat.protocolv2.reverse;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CertReport extends BaseCommandV2 {
    private static final long serialVersionUID = 1346546562534L;

    private String signCert;
    private String encryptCert;
}
