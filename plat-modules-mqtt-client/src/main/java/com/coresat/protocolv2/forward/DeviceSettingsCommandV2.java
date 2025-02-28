package com.coresat.protocolv2.forward;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.*;

/**
 * BroadcastContent
 *
 * @author Created by wangzaihong on 2024/08/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeviceSettingsCommandV2 extends BaseCommandV2 {

    private int siteMode;

    private Integer communicationLevel;
    private boolean enable4G;
    private boolean enableKu;
    private boolean enableL;
    private boolean enableBd;
    private boolean enableHorn;
    private boolean enableCamera;

}
