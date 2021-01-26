package com.leshiguang.arch.kunkka.web.config;

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
    private String proxyUrl;
    @Value("${proxy.targetUrl}")
    private String targetUrl;
    @Value("${proxy.loggingEnabled:true}")
    private String logging_enabled;
    @Value("${proxy.ignore:fonts*}")
    private String ignore;
    @Value("${proxy.index:index.html}")
    private String index;

    @Bean
    public ServletRegistrationBean proxyServletBean() {
        ServletRegistrationBean proxyServlet = new ServletRegistrationBean(new KunkkaProxyServlet(ignore, index), proxyUrl);
        proxyServlet.addInitParameter("targetUri", prefix + targetUrl);
        proxyServlet.addInitParameter(ProxyServlet.P_LOG, logging_enabled);
        proxyServlet.addUrlMappings("/redissonx");
        proxyServlet.setOrder(10);
        return proxyServlet;
    }
}
