package com.kabunx.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP 工具类
 */
public class HttpUtils {
    /**
     * 获取客户端真实IP
     */
    public static String getClientIp(HttpServletRequest request) {
        // nginx代理获取的真实用户ip
        String ip = request.getHeader("X-Real-IP");
        if (isInvalidIp(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况， 第一个IP为客户端真实IP，多个IP按照','分割
        if (!isInvalidIp(ip) && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }
        return ip;
    }

    /**
     * 是否无效的IP
     */
    private static boolean isInvalidIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return true;
        }
        return "unknown".equalsIgnoreCase(ip);
    }
}
