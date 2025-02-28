package com.coresat.plat.broker.persistance;

import com.coresat.plat.broker.constants.RedisConstant;
import com.coresat.plat.broker.handeler.INodeService;
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
public class RedisNodeServiceImpl implements INodeService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void registry(BrokerNode node) {
        redisTemplate.opsForSet().add(RedisConstant.NODE_KEY, node);
    }

    @Override
    public void removeNode(BrokerNode node) {
        redisTemplate.opsForSet().remove(RedisConstant.NODE_KEY, node);
    }

    @Override
    public Set<BrokerNode> getAllNodes() {
        Set<Object> members = redisTemplate.opsForSet().members(RedisConstant.NODE_KEY);
        if (CollectionUtils.isEmpty(members)) {
            return new HashSet<>();
        }
        return members.stream().map(s -> (BrokerNode) s).collect(Collectors.toSet());
    }
}
