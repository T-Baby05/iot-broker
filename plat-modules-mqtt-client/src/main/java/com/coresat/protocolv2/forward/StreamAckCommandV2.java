package com.coresat.protocolv2.forward;

import com.coresat.protocolv2.BaseCommandV2;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StreamAckCommandV2 extends BaseCommandV2 {

    /**
     * 信令交互序列号（上传协议中的序列号）
     */
    private Integer seq;
    /**
     * 0: 错误
     * 1：成功
     */
    private Byte result;
}
