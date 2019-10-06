package com.bluelion.shared.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ServerWebExchange;

public class RequestUtil {

    public static String getClientIp(ServerWebExchange exchange) {
        String ip = "";
        try {

            ip = exchange.getRequest().getHeaders().getFirst("x-forwarded-for");
//            LogContext.instance().info("x-forwarded-for:" + ip);
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = exchange.getRequest().getHeaders().getFirst("Proxy-Client-IP");
//                LogContext.instance().info("Proxy-Client-IP:" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = exchange.getRequest().getHeaders().getFirst("WL-Proxy-Client-IP");
//                LogContext.instance().info("WL-Proxy-Client-IP:" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = exchange.getRequest().getRemoteAddress().getHostString();
//                LogContext.instance().info("Remote-Addr:" + ip);
            }
            if (StringUtils.isNotEmpty(ip)) {
                String[] ipArray = ip.split(",");
                if (ipArray.length > 1) {
                    ip = "unknown".equals(ipArray[0].trim()) ? ipArray[1].trim() : ipArray[0].trim();
                }
            }
        } catch (Exception e) {
//            LogContext.instance().error(e, "获取IP失败");
        }
        return ip;
    }

}
