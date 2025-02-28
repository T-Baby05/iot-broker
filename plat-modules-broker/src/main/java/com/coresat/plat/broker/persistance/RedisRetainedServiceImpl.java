package com.coresat.plat.broker.persistance;

import com.alibaba.fastjson2.JSON;
import com.coresat.plat.broker.constants.RedisConstant;
import com.coresat.plat.broker.handeler.IRetainedMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RedisRetainedServiceImpl
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@Component
public class RedisRetainedServiceImpl implements IRetainedMessageService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    // 存储保留消息
    public void saveRetainedMessage(String topic, CustomRetainedMsg message) {
        log.debug("saveRetainedMessage() -> topic {}, message {}",topic,message);
        if (null == message.getPayload() || message.getPayload().length <= 0) {
            // 如果 payload 为空，删除保留消息
            removeRetainedMessage(topic);
        } else {
            redisTemplate.opsForValue().set(RedisConstant.RETAINED_MSG + topic, JSON.toJSONString(message));
        }
    }

    //  获取保留消息
    public CustomRetainedMsg getRetainedMessage(String topic) {
        String json = redisTemplate.opsForValue().get(RedisConstant.RETAINED_MSG + topic);
        log.debug("getRetainedMessage() -> topic {}, message {}",topic,json);
        return json == null ? null : JSON.parseObject(json, CustomRetainedMsg.class);
    }

    //  删除保留消息
    public void removeRetainedMessage(String topic) {
        log.debug("removeRetainedMessage() -> topic {}",topic);
        redisTemplate.delete(RedisConstant.RETAINED_MSG + topic);
    }
}
