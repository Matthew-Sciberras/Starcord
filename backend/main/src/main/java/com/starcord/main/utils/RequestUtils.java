package com.starcord.main.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getRequest() : null;
    }

    public static String getHeader(String header) {
        HttpServletRequest request = getCurrentRequest();
        assert request != null;
        return request.getHeader(header);
    }


    public static String getAuthorizationToken() {
        final String authHeader = getHeader("Authorization");
        return authHeader.substring(7); // remove "Bearer "
    }
}
