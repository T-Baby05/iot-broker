package com.coresat.plat.broker.persistance;

import com.coresat.plat.broker.constants.RedisConstant;
import com.coresat.plat.broker.handeler.ISubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * RedisSubscriptionsServiceImpl Redis topic订阅持久化方式
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@Component
public class RedisSubscriptionsServiceImpl implements ISubscriptionService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
//    @Resource
//    private DefaultRedisScript<Long> subscriptionScript;
@Resource
private ResourceLoader resourceLoader;


    @Override
    public void addNewSubscription(CustomSubscription subscription) {
        log.debug("addNewSubscription() -> clientId： {}, subscription: {}", subscription.getClientId(), subscription);
        redisTemplate.opsForSet().add(RedisConstant.SUBSCRIPTIONS_KEY + subscription.getClientId(), subscription);
    }

    @Override
    public void removeSubscription(String clientID) {
        log.debug("removeSubscription() -> clientId： {}", clientID);
        redisTemplate.delete(RedisConstant.SUBSCRIPTIONS_KEY + clientID);
    }

    @Override
    public void removeSubscription(String clientID, String topic) {
        log.debug("removeSubscription() -> clientId： {}, topic: {}", clientID, topic);
        redisTemplate.opsForSet().remove(RedisConstant.SUBSCRIPTIONS_KEY + clientID, topic);
    }

    @Override
    public Set<CustomSubscription> getSubscriptions(String clientId) {
        log.debug("getSubscriptions() -> clientId： {}", clientId);
        Set<Object> members = redisTemplate.opsForSet().members(RedisConstant.SUBSCRIPTIONS_KEY + clientId);
        if (CollectionUtils.isEmpty(members)) {
            return new HashSet<>();
        }
        return members.stream().map(s -> (CustomSubscription) s).collect(Collectors.toSet());
    }

    @Override
    public Long getSubscriptionsTotal() {

        ClassPathResource resource = new ClassPathResource("scripts/scan_subscriptions.lua");
        try (InputStream inputStream = resource.getInputStream()) {
            String luaScript = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            DefaultRedisScript<Long> script = new DefaultRedisScript<>(luaScript, Long.class);
            return redisTemplate.execute(script, Collections.emptyList());
        } catch (IOException e) {
            throw new RuntimeException("加载Lua脚本失败", e);
        }
    }
}
