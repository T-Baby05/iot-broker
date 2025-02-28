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
public class BroadcastCommandV2 extends BaseCommandV2 {

    private long bid;

    private String msgContent;

    private List<Object> audioList;
    private Integer volume;
}
