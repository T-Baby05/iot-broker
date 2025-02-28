package com.coresat.plat.broker.handeler;

import io.moquette.broker.security.IAuthorizatorPolicy;
import io.moquette.broker.subscriptions.Topic;
import org.springframework.stereotype.Component;


/**
 * 自定义的鉴权处理器
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Component
public class CustomAuthorizator implements IAuthorizatorPolicy {


    @Override
    public boolean canWrite(Topic topic, String s, String s1) {
        return false;
    }

    @Override
    public boolean canRead(Topic topic, String s, String s1) {
        return false;
    }
}