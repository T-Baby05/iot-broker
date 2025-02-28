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
public class DevVolumeCtrlCommandV2 extends BaseCommandV2 {

    // topic: device/%s/volume/ctrl
    private Integer volume;

}
