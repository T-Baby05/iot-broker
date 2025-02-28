package com.coresat.plat.broker.handeler;

import com.coresat.plat.broker.persistance.BrokerNode;

import java.util.List;
import java.util.Set;

/**
 * IMessageReceiver
 *
 * @author Created by wangzaihong on 2025/02/12
 */
public interface INodeService {

    /**
     * 注册集群节点信息
     */
    void registry(BrokerNode node);

    /**
     * 移除node
     *
     * @param node
     */
    void removeNode(BrokerNode node);

    /**
     * 获取集群节点列表
     *
     * @return {@link List }<{@link BrokerNode }>
     */
    Set<BrokerNode> getAllNodes();


}
