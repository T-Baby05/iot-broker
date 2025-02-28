package com.coresat.runner;

import com.coresat.mqtt.client.MqttCustomerClient;
import com.coresat.mqtt.client.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * MqttClientSimulator
 *
 * @author Created by wangzaihong on 2024/11/21
 */
@Slf4j
@Component
public class MqttClientSimulator implements CommandLineRunner {

    @Resource
    private MqttProperties mqttProperties;

    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void run(String... args) {
        // 初始化连接池
        threadPoolExecutor = new ThreadPoolExecutor(20, 3500, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3520));
        List<String> collect = mqttProperties.getClientIds().stream().distinct().collect(Collectors.toList());


        collect.forEach(clientId -> threadPoolExecutor.execute(() -> {
            // 创建自定义mqtt客户端
            MqttCustomerClient mqttCustomerClient = new MqttCustomerClient(mqttProperties);
            mqttCustomerClient.initializeMqttClient(clientId);
            // 连接emqx服务器
            mqttCustomerClient.connect();
            log.info("device {} connect server and subscribe topic success", clientId);
        }));
    }

    private String getDeviceNo(int num, int len) {
        String res = "";
        int nLen = Integer.valueOf(num).toString().length();
        if (nLen < len) {
            for (int j = 0; j < len - nLen; j++) {
                res = "0" + res;
            }
            res += Integer.valueOf(num).toString();
        }
        return res;
    }
}
