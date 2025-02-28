package com.coresat.plat.broker.persistance;

import com.coresat.plat.broker.constants.RedisConstant;
import com.coresat.plat.broker.handeler.ISessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * RedisSessionServiceImpl
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@Component
public class RedisSessionServiceImpl implements ISessionService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Set<CustomSession> getSessions(String keyword) {
        log.debug("getSessions()");
        Map<Object, Object> sessionMap = redisTemplate.opsForHash().entries(RedisConstant.SESSION_KEY);
        if (CollectionUtils.isEmpty(sessionMap)) {
            return new HashSet<>();
        }
        String finalKeyword = StringUtils.hasText(keyword) ? keyword : "";
        return sessionMap.values().stream().map(s -> (CustomSession) s).filter(session -> session.getClientId().contains(finalKeyword)).collect(Collectors.toSet());

    }

    public void saveSession(String clientId, CustomSession session) {
        log.info("saveSession() -> clientId {}", clientId);
        redisTemplate.opsForHash().put(RedisConstant.SESSION_KEY, clientId, session);
    }

    public void removeSession(String clientId) {
        log.info("removeSession() -> clientId {}", clientId);
        redisTemplate.opsForHash().delete(RedisConstant.SESSION_KEY, clientId);
    }

    public boolean isOnline(String clientId) {
        log.debug("isOnline() -> clientId {}", clientId);
        return redisTemplate.opsForHash().hasKey(RedisConstant.SESSION_KEY, clientId);
    }

    @Override
    public CustomSession getSession(String clientId) {
        log.debug("getSession() -> clientId {}", clientId);
        Object o = redisTemplate.opsForHash().get(RedisConstant.SESSION_KEY, clientId);
        if (null == o) {
            return null;
        }
        return (CustomSession) o;
    }
}
