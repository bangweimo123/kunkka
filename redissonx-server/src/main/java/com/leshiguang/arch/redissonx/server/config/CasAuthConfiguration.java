package com.leshiguang.arch.redissonx.server.config;

import com.leshiguang.arch.cas.support.fliter.FilterChainProxy;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-19 19:22
 * @Modify
 */
@Configuration
@EnableRedisHttpSession
public class CasAuthConfiguration {

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Resource
    private FilterChainProxy casFilterChainProxy;
    @Resource
    private ServletRegistrationBean proxyServletBean;

    @Bean
    public FilterRegistrationBean casFilterChainProxyFilter() {
        FilterRegistrationBean casFilterChainProxyFilter = new FilterRegistrationBean(casFilterChainProxy, proxyServletBean);
        casFilterChainProxyFilter.addUrlPatterns("/*");
        casFilterChainProxyFilter.setOrder(1);
        return casFilterChainProxyFilter;
    }
}
