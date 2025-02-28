package com.coresat.plat.broker.sender;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RocketMQProducer
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@Component
public final class RocketMQProducer {


    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送消息到指定 topic
     */
    public void sendMessage(String topic, String message) {
        rocketMQTemplate.convertAndSend(topic, message);
        log.info("发送消息到 topic " + topic + " ：" + message);
    }

}
