package com.coresat.protocolv2.reverse;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LStatusResponse extends BaseCommandV2 {
    private Long recvFreq;
    private Long symbolRate;
    private Integer rolloff;
}
