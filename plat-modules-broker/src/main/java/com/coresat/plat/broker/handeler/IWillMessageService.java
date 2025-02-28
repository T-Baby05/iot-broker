package com.coresat.plat.broker.handeler;

import com.coresat.plat.broker.persistance.CustomWillMsg;

import java.util.List;

/**
 * IMessageReceiver
 *
 * @author Created by wangzaihong on 2025/02/12
 */
public interface IWillMessageService {

    void saveWill(String clientId, CustomWillMsg willMessage);

    void removeWill(String clientId);

    CustomWillMsg getWill(String clientId);


    List<CustomWillMsg> getAllWillMessages();


}
