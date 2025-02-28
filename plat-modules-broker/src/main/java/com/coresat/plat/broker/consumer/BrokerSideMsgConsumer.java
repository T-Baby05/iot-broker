package com.coresat.plat.broker.consumer;

import com.alibaba.fastjson.JSON;
import com.coresat.plat.api.broker.CommonMqttMessage;
import com.coresat.plat.api.broker.topic.RocketmqTopic;
import io.moquette.broker.Server;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * BrokerSideMsgConsumer Broker侧的消费者
 * 用于处理平台下发给终端的消息，采用Rocketmq的广播模式，每个broker都尝试转发消息给自己维护的订阅者
 *
 * @author Created by wangzaihong on 2025/02/26
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = RocketmqTopic.SERVER_MESSAGE_TOPIC, consumerGroup = "server-message-group", messageModel = MessageModel.BROADCASTING)
public class BrokerSideMsgConsumer implements RocketMQListener<String> {

    private final Server server;

    @Autowired
    public BrokerSideMsgConsumer(Server server) {
        this.server = server;
    }

    @Override
    public void onMessage(String message) {
        log.info("receive plat message, start to dispatch...");
        log.info("message: {}", message);
        try {
            CommonMqttMessage mqttMessage = JSON.parseObject(message, CommonMqttMessage.class);
            String payload = StringUtils.hasText(mqttMessage.getPayload()) ? mqttMessage.getPayload() : "";
            server.internalPublish(MqttMessageBuilders.publish()
                    .topicName(mqttMessage.getTopic())
                    .retained(false)
                    .qos(MqttQoS.valueOf(mqttMessage.getQos()))
                    .payload(Unpooled.copiedBuffer(payload.getBytes(StandardCharsets.UTF_8)))
                    .build(), mqttMessage.getClientId());
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return;
        }
        log.debug("message send out");

    }
}
