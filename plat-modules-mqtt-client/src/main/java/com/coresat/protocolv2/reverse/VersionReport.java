package com.coresat.protocolv2.reverse;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.Builder;
import lombok.Data;

/**
 * StatusReport 版本上报指令
 *
 * @author Created by wangzaihong on 2024/10/25
 */
@Data
@Builder
public class VersionReport extends BaseCommandV2 {

    private String cid;
    // 路由板卡
    private String routerSoftVersion;
    private String routerFirmVersion;
    // 数据网关
    private String gatewaySoftVersion;
    private String gatewayFirmVersion;
    // L波段
    private String lSoftVersion;
    private String lFirmVersion;
    // 北斗MCU
    private String mcuSoftVersion;
    private String mcuFirmVersion;
    // 北斗RDSS
    private String rdssSoftVersion;
    private String rdssFirmVersion;

}
