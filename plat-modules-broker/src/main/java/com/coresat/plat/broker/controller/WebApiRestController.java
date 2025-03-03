package com.coresat.plat.broker.controller;


import com.alibaba.fastjson.JSON;
import com.coresat.plat.api.broker.CommonMqttMessage;
import com.coresat.plat.api.broker.auth.AuthRequest;
import com.coresat.plat.api.broker.auth.AuthResponse;
import com.coresat.plat.broker.handeler.*;
import com.coresat.plat.broker.persistance.*;
import com.coresat.plat.broker.sender.RocketMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * MessagePusherController
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@RestController()
@RequestMapping("/api")
public class WebApiRestController {

    @Resource
    private INodeService nodeService;
    @Resource
    private RocketMQProducer rocketMQProducer;
    @Resource
    private ISessionService sessionService;
    @Resource
    private ISubscriptionService subscriptionService;
    @Resource
    private IRetainedMessageService retainedMessageService;
    @Resource
    private IWillMessageService willMessageService;

    @PostMapping("/mqtt/auth")
    AuthResponse publishMessage(@RequestBody AuthRequest request) {
        boolean a = "coresat".equals(request.getUsername());
        log.info("http auth method called, result: {}", a);
        if (a) {
            return new AuthResponse(a, null);
        }
        return new AuthResponse(false, "Invalid username or password");
    }

    @PostMapping("/message/publish")
    void publishMessage(@RequestBody CommonMqttMessage mqttMessage) {
        rocketMQProducer.sendMessage("server-message-topic", JSON.toJSONString(mqttMessage));
    }

    // 获取BROKER节点信息
    @GetMapping("/node/list")
    public ResponseEntity<Set<BrokerNode>> getNodeList() {
        Set<BrokerNode> allNodes = nodeService.getAllNodes();
//        List<String> strings = Arrays.asList("192.168.111.111@1883","192.168.111.112@1883");
        return ResponseEntity.ok(allNodes);
    }

    // 获取所有在线设备
    @GetMapping("/sessions/search")
    public ResponseEntity<Set<CustomSession>> getSessions(String keyword) {
        Set<CustomSession> sessions = sessionService.getSessions(keyword);
        return ResponseEntity.ok(sessions);
    }

    // 获取所有在线设备
    @GetMapping("/sessions/total")
    public ResponseEntity<Integer> getSessionsTotal() {
        Set<CustomSession> sessions = sessionService.getSessions("");
        return ResponseEntity.ok(sessions.size());
    }

    // 获取设备订阅总数
    @GetMapping("/subscriptions/total")
    public ResponseEntity<Long> getSubscriptionsTotal() {
        Long total = subscriptionService.getSubscriptionsTotal();
        return ResponseEntity.ok(total);
    }


    // 获取单个设备会话信息
    @GetMapping("/sessions/{clientId}")
    public ResponseEntity<CustomSession> getSession(@PathVariable String clientId) {
        CustomSession session = sessionService.getSession(clientId);
        return session != null ? ResponseEntity.ok(session) : ResponseEntity.notFound().build();
    }

    // 强制断开设备连接
    @DeleteMapping("/sessions/{clientId}")
    public ResponseEntity<String> disconnectClient(@PathVariable String clientId) {
        sessionService.removeSession(clientId);
        subscriptionService.removeSubscription(clientId);
        return ResponseEntity.ok("设备 " + clientId + " 已断开");
    }

    // 获取设备订阅的 Topic
    @GetMapping("/subscriptions/{clientId}")
    public ResponseEntity<Set<CustomSubscription>> getSubscriptions(@PathVariable String clientId) {
        Set<CustomSubscription> topics = subscriptionService.getSubscriptions(clientId);
        return ResponseEntity.ok(topics);
    }

    // ✅ 获取所有遗嘱消息
    @GetMapping("/willMessages")
    public ResponseEntity<List<CustomWillMsg>> getWillMessages() {
        List<CustomWillMsg> willMessages = willMessageService.getAllWillMessages();
        return ResponseEntity.ok(willMessages);
    }

    // ✅ 获取某个 Topic 的保留消息
    @GetMapping("/retained/{topic}")
    public ResponseEntity<CustomRetainedMsg> getRetainedMessage(@PathVariable String topic) {
        CustomRetainedMsg retainedMessage = retainedMessageService.getRetainedMessage(topic);
        return retainedMessage != null ? ResponseEntity.ok(retainedMessage) : ResponseEntity.notFound().build();
    }

}
