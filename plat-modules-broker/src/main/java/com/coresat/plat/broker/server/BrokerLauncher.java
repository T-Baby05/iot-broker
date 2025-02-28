package com.coresat.plat.broker.server;

import com.coresat.plat.broker.handeler.CustomAuthenticator;
import com.coresat.plat.broker.handeler.INodeService;
import com.coresat.plat.broker.persistance.BrokerNode;
import com.coresat.plat.broker.utils.HostInfoUtil;
import io.moquette.broker.*;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import io.moquette.interception.AbstractInterceptHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * BrokerLauncher
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@Component
public class BrokerLauncher extends Server implements DisposableBean {

//    @Value("${mqtt.port}")
//    private String mqttPort;
//    @Value("${mqtt.data_path}")
//    private String dataPath;

    @Resource
    private INodeService nodeService;
    @Resource
    private CustomAuthenticator customAuthenticator;
    @Autowired
    Map<String, AbstractInterceptHandler> handlerMap;

    private BrokerNode brokerNode;

    @Bean
    public Server server() throws Exception {
        IResourceLoader classpathLoader = new ClasspathResourceLoader("config/moquette.conf");
        final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
//        classPathConfig.setProperty(IConfig.PORT_PROPERTY_NAME, mqttPort);
//        classPathConfig.setProperty(IConfig.DATA_PATH_PROPERTY_NAME, dataPath);
        Server mqttBroker = new Server();
        // 添加拦截器
//        Map<String, AbstractInterceptHandler> beansOfType = applicationContext.getBeansOfType(AbstractInterceptHandler.class);
        ArrayList<AbstractInterceptHandler> handlers = new ArrayList<>(handlerMap.values());


        mqttBroker.startServer(classPathConfig, handlers, null, customAuthenticator, null);
        //注册Node信息
        brokerNode = new BrokerNode(HostInfoUtil.getLocalIP().concat("@").concat(classPathConfig.getProperty(IConfig.PORT_PROPERTY_NAME)));
        nodeService.registry(brokerNode);
        //Bind  a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping broker");
            mqttBroker.stopServer();
            System.out.println("Broker stopped");
        }));


        return mqttBroker;
    }

    @Override
    public void destroy() throws Exception {
        // 程序关闭时移除node
        nodeService.removeNode(brokerNode);
    }
}
