package com.leshiguang.arch.redissonx.server.config;

import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-17 12:47
 * @Modify
 */
public class RedissonxProxyServlet extends ProxyServlet {
    @Override
    protected String rewriteUrlFromRequest(HttpServletRequest servletRequest) {
        StringBuilder uri = new StringBuilder(500);
        uri.append(getTargetUri(servletRequest));
        return uri.toString();
    }
}
