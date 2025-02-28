package com.coresat.plat.broker.consumer;

import com.coresat.plat.api.broker.topic.RocketmqTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * PlatSideCommonMsgConsumer 平台侧的普通消息消费者
 *
 * @author Created by wangzaihong on 2025/02/26
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = RocketmqTopic.CLIENT_MESSAGE_TOPIC, consumerGroup = "client-message-group", consumeMode = ConsumeMode.CONCURRENTLY, messageModel = MessageModel.CLUSTERING)
public class PlatSideCommonMsgConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("receive client message: {}", message);
    }
}
