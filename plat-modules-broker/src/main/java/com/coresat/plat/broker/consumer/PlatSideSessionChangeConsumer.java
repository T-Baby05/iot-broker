package com.coresat.plat.broker.consumer;

import com.coresat.plat.api.broker.topic.RocketmqTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * PlatSideSessionChangeConsumer 平台侧的终端状态消息消费者
 *
 * @author Created by wangzaihong on 2025/02/26
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = RocketmqTopic.SESSION_CHANGE_TOPIC, consumerGroup = "session-message-group", consumeMode = ConsumeMode.ORDERLY, messageModel = MessageModel.CLUSTERING)
public class PlatSideSessionChangeConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("receive client status  message: {}", message);
    }
}
