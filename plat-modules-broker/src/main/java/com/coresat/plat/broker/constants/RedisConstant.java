package com.coresat.plat.broker.constants;

/**
 * RedisConstant
 *
 * @author Created by wangzaihong on 2025/02/12
 */
public class RedisConstant {

    /**
     * mqtt:session	存储会话基本信息（CleanSession、LWT）。
     */
    public static final String SESSION_KEY ="mqtt:sessions";

    /**
     * mqtt:subscriptions:<clientId>   </>	存储客户端订阅关系（主题、QoS）
     */
    public static final String SUBSCRIPTIONS_KEY ="mqtt:subscriptions:";

    /**
     * mqtt:nodes   </>	存储客户端订阅关系（主题、QoS）
     */
    public static final String NODE_KEY ="mqtt:nodes";

    /**
     * mqtt:wills_msg	遗嘱消息
     */
    public static final String WILL_MSG ="mqtt:will_msg";

    /**
     * mqtt:retained:<topic>	存储客户端保留消息
     */
    public static final String RETAINED_MSG ="mqtt:retained:";

    /**
     * mqtt:queues	存储队列
     */
    public static final String QUEUE_SET_KEY ="mqtt:queues";

    /**
     * mqtt:queue:<clientId>	存储队列
     */
    public static final String QUEUE_PREFIX_KEY ="mqtt:queue:";


}
