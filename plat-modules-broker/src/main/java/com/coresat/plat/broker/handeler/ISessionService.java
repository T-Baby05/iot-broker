package com.coresat.plat.broker.handeler;

import com.coresat.plat.broker.persistance.CustomSession;

import java.util.Set;

/**
 * IMessageReceiver
 *
 * @author Created by wangzaihong on 2025/02/12
 */
public interface ISessionService {

    /**
     * @return the full list of persisted sessions data.
     */
    Set<CustomSession> getSessions(String keyword);

    /**
     * Save data composing a session, es MQTT version, creation date and properties but not queues or subscriptions.
     */
    void saveSession(String clientId,CustomSession session);

    void removeSession(String clientId);

    boolean isOnline(String clientId);


    CustomSession getSession(String clientId);
}
