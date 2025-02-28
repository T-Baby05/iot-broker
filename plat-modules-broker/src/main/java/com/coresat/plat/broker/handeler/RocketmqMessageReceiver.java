package com.coresat.plat.broker.handeler;

import com.coresat.plat.api.broker.topic.RocketmqTopic;
import com.coresat.plat.broker.sender.RocketMQProducer;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * RocketmqMessageReceiver
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@Component
public class RocketmqMessageReceiver implements IMessageReceiver {
    @Resource
    private RocketMQProducer rocketMQProducer;

    @Override
    public void handle(InterceptPublishMessage msg) {
        ByteBuf payloadBuf = msg.getPayload();
        byte[] bytes = new byte[payloadBuf.readableBytes()];
        payloadBuf.getBytes(payloadBuf.readerIndex(), bytes);
        String payload = new String(bytes, StandardCharsets.UTF_8);
        log.debug("Received message from client {} on topic {}, payload is [{}]", msg.getClientID(), msg.getTopicName(), payload);
        rocketMQProducer.sendMessage(RocketmqTopic.CLIENT_MESSAGE_TOPIC, payload);
    }
}
