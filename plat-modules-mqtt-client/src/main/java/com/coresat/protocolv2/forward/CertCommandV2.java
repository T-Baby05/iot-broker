package com.coresat.protocolv2.forward;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CertCommandV2 extends BaseCommandV2 {

    private String cert;
}
