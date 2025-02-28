package com.coresat.plat.broker.handeler;

import com.coresat.plat.api.broker.auth.AuthRequest;
import com.coresat.plat.api.broker.auth.AuthResponse;
import io.moquette.broker.security.IAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


/**
 * 自定义认证处理器
 *
 * @author Created by wangzaihong on 2025/02/12
 */
@Slf4j
@Component
public class CustomAuthenticator implements IAuthenticator {

    private static final String DEFAULT_TYPE = "default";
    private static final String HTTP_TYPE = "http";

    @Resource
    private AuthConfig authConfig;
    @Resource
    private RestTemplate restTemplate;

    @Override
    public boolean checkValid(String clientId, String username, byte[] password) {
        log.debug("Device {} login request, username: {}, password: {}", clientId, username, new String(password));
        String authType = null == authConfig.getType() ? "default" : authConfig.getType();
        switch (authType) {
            case DEFAULT_TYPE:
                log.debug("No authentication type configured, return 'true'");
                return true;
            case HTTP_TYPE:
                boolean b = authenticateWithHttp(clientId, username, new String(password));
                log.debug("Use http authentication type, result: {}",b);
                return b;
            default:
                log.warn("Unknown authentication type: {}", authType);
                return false;
        }
    }

    private boolean authenticateWithHttp(String clientId, String username, String password) {
        try {
            String url = authConfig.getHttpUrl();
            AuthRequest request = new AuthRequest(clientId, username, password);
            AuthResponse response = restTemplate.postForObject(url, request, AuthResponse.class);
            return response != null && response.isAuthenticated();
        } catch (Exception e) {
            log.error("HTTP authentication failed", e);
            return false;
        }
    }
}