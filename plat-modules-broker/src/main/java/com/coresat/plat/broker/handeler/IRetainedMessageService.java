package com.coresat.plat.broker.handeler;

import com.coresat.plat.broker.persistance.CustomRetainedMsg;

/**
 * IMessageReceiver
 *
 * @author Created by wangzaihong on 2025/02/12
 */
public interface IRetainedMessageService {

    // 存储保留消息
    void saveRetainedMessage(String topic, CustomRetainedMsg message);

    // 获取保留消息
    CustomRetainedMsg getRetainedMessage(String topic);

    // 删除保留消息
    void removeRetainedMessage(String topic);

}
