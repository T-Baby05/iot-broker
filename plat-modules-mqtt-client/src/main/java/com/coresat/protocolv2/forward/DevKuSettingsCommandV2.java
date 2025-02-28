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
public class DevKuSettingsCommandV2 extends BaseCommandV2 {

    private Long recvFreq;
    private Long symbolRate;

    private Integer rolloff;

}
