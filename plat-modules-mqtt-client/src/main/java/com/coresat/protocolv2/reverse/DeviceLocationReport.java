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
public class DeviceLocationReport extends BaseCommandV2 {

    private String cid;
    private Float lon;

    private Float lat;

    private Long regionId;

    private String location;

}
