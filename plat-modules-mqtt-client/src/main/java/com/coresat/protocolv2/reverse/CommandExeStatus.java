package com.coresat.protocolv2.reverse;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * StatusReport 状态上报指令
 *
 * @author Created by wangzaihong on 2024/10/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CommandExeStatus extends BaseCommandV2 {
    private Long bid;

    private String cmd;

}
