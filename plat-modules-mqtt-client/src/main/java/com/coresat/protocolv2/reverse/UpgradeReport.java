package com.coresat.protocolv2.reverse;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * StatusReport
 *
 * @author Created by wangzaihong on 2024/10/25
 */
@Data
@AllArgsConstructor
public class UpgradeReport extends BaseCommandV2 {

    private String cid;
    private Long imageId;
    /**
     * 0:升级成功 1:升级失败
     */
    private Short status;

    private String reason;




}
