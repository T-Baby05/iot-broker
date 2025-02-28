package com.coresat.plat.broker.handeler;

import io.moquette.interception.messages.InterceptPublishMessage;

/**
 * IMessageReceiver
 *
 * @author Created by wangzaihong on 2025/02/12
 */
public interface IMessageReceiver {

    void handle(InterceptPublishMessage msg);


}
