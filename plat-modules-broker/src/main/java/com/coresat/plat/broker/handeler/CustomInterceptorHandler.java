package com.coresat.plat.broker.handeler;

import com.alibaba.fastjson2.JSON;
import com.coresat.plat.api.broker.CommonMqttMessage;
import com.coresat.plat.api.broker.SessionCreateMessage;
import com.coresat.plat.api.broker.SessionRemoveMessage;
import com.coresat.plat.api.broker.topic.RocketmqTopic;
import com.coresat.plat.broker.persistance.CustomRetainedMsg;
import com.coresat.plat.broker.persistance.CustomSession;
import com.coresat.plat.broker.persistance.CustomSubscription;
import com.coresat.plat.broker.persistance.CustomWillMsg;
import com.coresat.plat.broker.sender.RocketMQProducer;
import com.coresat.plat.broker.utils.HostInfoUtil;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.*;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * 自定义MQTT消息发布拦截器
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@Component
public class CustomInterceptorHandler extends AbstractInterceptHandler {
    @Value("${mqtt.port}")
    private String mqttPort;
    @Resource
    private IMessageReceiver messageReceiver;
    @Resource
    private ISessionService sessionService;
    @Resource
    private ISubscriptionService subscriptionService;
    @Resource
    private IRetainedMessageService retainedMessageService;
    @Resource
    private IWillMessageService willMessageService;
    @Resource
    private RocketMQProducer rocketMQProducer;


    @Override
    public String getID() {
        return "CustomInterceptorHandler";
    }


    @Override
    public Class<?>[] getInterceptedMessageTypes() {
        return super.getInterceptedMessageTypes();
    }

    @Override
    public void onConnect(InterceptConnectMessage msg) {
        log.info("{} connect", msg.getClientID());
        //保存session
        CustomSession customSession =
                new CustomSession(msg.getClientID(), getNodeId(), msg.isCleanSession(), msg.getKeepAlive(),
                        msg.getUsername(), new String(msg.getPassword()), System.currentTimeMillis());
        sessionService.saveSession(msg.getClientID(), customSession);
        // 发布消息告诉业务系统设备上线
        rocketMQProducer.sendMessage(RocketmqTopic.SESSION_CHANGE_TOPIC, JSON.toJSONString(new SessionCreateMessage(msg.getClientID())));
        // 设备上线时需要判断是否发布了遗嘱消息，如果是需要存储
        if (msg.isWillFlag()) {
            // 处理遗嘱消息
            CustomWillMsg customWillMsg = new CustomWillMsg(msg.getClientID(), msg.getWillTopic(), msg.getWillQos(), msg.getWillMessage());
            willMessageService.saveWill(msg.getClientID(), customWillMsg);
        }
    }

    private String getNodeId() {
        return HostInfoUtil.getLocalIP().concat("@").concat(mqttPort);
    }

    @Override
    public void onDisconnect(InterceptDisconnectMessage msg) {
        log.info("{} onDisconnect", msg.getClientID());
        // 设备离线时移除session
        sessionService.removeSession(msg.getClientID());
        // 发布消息告诉业务系统设备离线
        rocketMQProducer.sendMessage(RocketmqTopic.SESSION_CHANGE_TOPIC, JSON.toJSONString(new SessionRemoveMessage(msg.getClientID())));
        // 设备离线时移除订阅
        subscriptionService.removeSubscription(msg.getClientID());
        // 设备正常离线删除遗嘱消息
        willMessageService.removeWill(msg.getClientID());

    }

    @Override
    public void onConnectionLost(InterceptConnectionLostMessage msg) {
        String clientID = msg.getClientID();
        log.info("{} onConnectionLost", clientID);
        sessionService.removeSession(clientID);
        // 发布消息告诉业务系统设备离线
        rocketMQProducer.sendMessage(RocketmqTopic.SESSION_CHANGE_TOPIC, JSON.toJSONString(new SessionRemoveMessage(msg.getClientID())));
        // 移除终端的订阅信息
        subscriptionService.removeSubscription(clientID);
        // 客户端意外离线后需要检测是否存在遗嘱消息，存在则发布
        CustomWillMsg will = willMessageService.getWill(clientID);
        if (Objects.nonNull(will)) {
            CommonMqttMessage mqttMessage = new CommonMqttMessage(will.getClientId(), will.getTopicFilter(), will.getRequestedQos(), new String(will.getPayload()));
            rocketMQProducer.sendMessage(RocketmqTopic.WILL_MESSAGE_TOPIC, JSON.toJSONString(mqttMessage));
            willMessageService.removeWill(clientID);
        }
    }

    @Override
    public void onPublish(InterceptPublishMessage msg) {
        //判断消息是否为保留消息
        if (msg.isRetainFlag()) {
            ByteBuf payloadBuf = msg.getPayload();
            byte[] bytes = new byte[payloadBuf.readableBytes()];
            payloadBuf.getBytes(msg.getPayload().readerIndex(), bytes);
            CustomRetainedMsg customRetainedMsg = new CustomRetainedMsg(msg.getTopicName(), msg.getQos().value(), bytes);
            retainedMessageService.saveRetainedMessage(msg.getTopicName(), customRetainedMsg);
            return;
        }
        // 处理消息
        messageReceiver.handle(msg);
    }

    @Override
    public void onSubscribe(InterceptSubscribeMessage msg) {
        log.debug("{} subscribe topic {}", msg.getClientID(), msg.getTopicFilter());
        CustomSubscription subscription = new CustomSubscription(msg.getClientID(), msg.getTopicFilter(), msg.getRequestedQos());
        subscriptionService.addNewSubscription(subscription);

        // 检查是否有保留消息
        CustomRetainedMsg retainedMsg = retainedMessageService.getRetainedMessage(msg.getTopicFilter());
        if (retainedMsg != null) {
            CommonMqttMessage mqttMessage = new CommonMqttMessage(null, retainedMsg.getTopic(), retainedMsg.getQos(), new String(retainedMsg.getPayload()));
            rocketMQProducer.sendMessage(RocketmqTopic.RETAINED_MESSAGE_TOPIC, JSON.toJSONString(mqttMessage));

        }
    }

    @Override
    public void onUnsubscribe(InterceptUnsubscribeMessage msg) {
        log.debug("{} unSubscribe topic {}", msg.getClientID(), msg.getTopicFilter());
        subscriptionService.removeSubscription(msg.getClientID(), msg.getTopicFilter());
    }

    @Override
    public void onMessageAcknowledged(InterceptAcknowledgedMessage msg) {
    }

    @Override
    public void onSessionLoopError(Throwable error) {

    }

}
