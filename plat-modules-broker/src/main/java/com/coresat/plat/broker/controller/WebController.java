package com.coresat.plat.broker.controller;


import com.coresat.plat.broker.handeler.ISessionService;
import com.coresat.plat.broker.persistance.CustomSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Set;

/**
 * MessagePusherController
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Controller
public class WebController {

    @Resource
    private ISessionService sessionService;

    @RequestMapping("/index")
    public String index(Model model) {
        Set<CustomSession> sessions = sessionService.getSessions("");
        model.addAttribute("sessions", sessions);
        return "index";
    }
}
