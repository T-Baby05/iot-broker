package com.coresat.protocolv2.forward;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.*;

import java.util.List;

/**
 * BroadcastContent
 *
 * @author Created by wangzaihong on 2024/08/09
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OtaUpgradeCommandV2 extends BaseCommandV2 {

    private Long imageId;
    // 1:网关py文件 2：网关bin文件  3:路由 4:北斗MCU 5:北斗RDSS 6:L模块
    private Short type;
    private String softVersion;
    private String firmVersion;
    private List<UrlObj> urls;


    @Data
    @AllArgsConstructor
    public static class UrlObj {
        private String fileName;
        private String url;
    }

}
