package com.coresat.plat.api.broker.topic;

/**
 * RocketmqTopic
 *
 * @author Created by wangzaihong on 2025/02/12
 */
public class RocketmqTopic {

    /**
     * 存储平台下发的消息
     */
    public static final String SERVER_MESSAGE_TOPIC = "server-message-topic";

    /**
     * 存储终端发回的消息
     */
    public static final String CLIENT_MESSAGE_TOPIC = "client-message-topic";


    /**
     * 存储遗嘱消息
     */
    public static final String WILL_MESSAGE_TOPIC = "will-message-topic";

    /**
     * 存储保留消息
     */
    public static final String RETAINED_MESSAGE_TOPIC = "retained-message-topic";


    /**
     * 存储保留消息
     */
    public static final String SESSION_CHANGE_TOPIC = "session-change-topic";


}
