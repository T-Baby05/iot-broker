package com.coresat.protocolv2.reverse;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * StatusReport 状态上报指令
 *
 * @author Created by wangzaihong on 2024/10/25
 */
@Data
@AllArgsConstructor
public class KuStatusResponse extends BaseCommandV2 {
    private String cid;
    private Long recvFreq;
    private Long symbolRate;
    private Integer rolloff;
    private Integer lockStatus;
    private Integer kuNetStatus;
}
