package com.coresat.plat.broker.persistance;

import com.coresat.plat.broker.constants.RedisConstant;
import com.coresat.plat.broker.handeler.IWillMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author wangzaihong
 * @date 2025/02/24
 */
@Slf4j
@Component
public class RedisWillMessageServiceImpl implements IWillMessageService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public void saveWill(String clientId, CustomWillMsg willMessage) {

        log.info("saveWill() -> clientId：{} willMessage {}", clientId, willMessage);
        redisTemplate.opsForHash().put(RedisConstant.WILL_MSG, clientId, willMessage);
    }

    public void removeWill(String clientId) {
        log.info("removeWill() -> clientId：{}", clientId);
        redisTemplate.opsForHash().delete(RedisConstant.WILL_MSG, clientId);
    }

    public CustomWillMsg getWill(String clientId) {
        log.debug("getWill() -> clientId：{}", clientId);
        Object o = redisTemplate.opsForHash().get(RedisConstant.WILL_MSG, clientId);
        if (null == o) {
            return null;
        }
        return (CustomWillMsg) o;
    }


    @Override
    public List<CustomWillMsg> getAllWillMessages() {
        log.debug("getAllWillMessages()");
        Map<Object, Object> willMap = redisTemplate.opsForHash().entries(RedisConstant.SESSION_KEY);
        if (CollectionUtils.isEmpty(willMap)) {
            return new ArrayList<>();
        }
        return willMap.values().stream().map(s -> (CustomWillMsg) s).collect(Collectors.toList());

    }
}
