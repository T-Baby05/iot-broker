package com.coresat.plat.broker.handeler;

import com.coresat.plat.broker.persistance.CustomSubscription;

import java.io.IOException;
import java.util.Set;

/**
 * IMessageReceiver
 *
 * @author Created by wangzaihong on 2025/02/12
 */
public interface ISubscriptionService {


    void addNewSubscription(CustomSubscription subscription);

    void removeSubscription(String clientID);

    void removeSubscription(String clientID,String topic );

    Set<CustomSubscription> getSubscriptions(String clientId);

    Long getSubscriptionsTotal();
}
