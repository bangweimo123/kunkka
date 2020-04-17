package com.leshiguang.arch.redissonx.server.config;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyServletConfiguration {

    @Value("${proxy.prefix}")
    private String prefix;
    @Value("${proxy.serverUrl}")
    private String servlet_url;

    @Value("${proxy.targetUrl}")
    private String target_url;

    @Value("${proxy.loggingEnabled:true}")
    private String logging_enabled;

    @Bean
    public ServletRegistrationBean proxyServletBean() {
        ServletRegistrationBean proxyServlet = new ServletRegistrationBean(new RedissonxProxyServlet(), servlet_url);
        proxyServlet.addInitParameter("targetUri", prefix + target_url);
        proxyServlet.addInitParameter(ProxyServlet.P_LOG, logging_enabled);
        proxyServlet.setLoadOnStartup(2);
        return proxyServlet;
    }
}
