package com.coresat.protocolv2.reverse;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.Builder;
import lombok.Data;

/**
 * StatusReport 状态上报指令
 *
 * @author Created by wangzaihong on 2024/10/25
 */
@Data
@Builder
public class StatusReport extends BaseCommandV2 {
    private String cid;
    private String g4Num;
    private String bdNum;
    private Float lon;
    private Float lat;
    private Float soc;
    private Float voltage;
    private String coma;
    private Integer d4G;
    private Integer dBD;
    private Double srL;
    private Double srKU;
    private Integer lockStatus;
    private Integer kuNetStatus;
}
