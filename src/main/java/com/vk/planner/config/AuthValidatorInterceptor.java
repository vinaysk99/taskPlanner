package com.vk.planner.config;

import com.vk.planner.exception.AuthException;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthValidatorInterceptor implements HandlerInterceptor {

    private Map<String, String> validUserNamePasswords;

    public AuthValidatorInterceptor() {
        validUserNamePasswords = new HashMap<>();
        validUserNamePasswords.put("vk", "jsr");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorizationHeader)) {
            return false;
        }

        String auth = authorizationHeader.substring(6);

        byte[] decodedBytes = Base64.getDecoder().decode(auth);
        String decodedString = new String(decodedBytes);
        String userName = decodedString.split(":")[0];
        String password = decodedString.split(":")[1];

        if (!(validUserNamePasswords.containsKey(userName) && validUserNamePasswords.get(userName).equals(password))) {
            throw new AuthException("Invalid Auth header");
        }

        return true;
    }
}
