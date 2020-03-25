package com.leshiguang.arch.redissonx.server.config;

import com.leshiguang.arch.cas.support.fliter.FilterChainProxy;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.Resource;

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

    @Bean
    public FilterRegistrationBean casFilterChainProxyFilter() throws Exception {
        FilterRegistrationBean casFilterChainProxyFilter = new FilterRegistrationBean(casFilterChainProxy);
        casFilterChainProxyFilter.addUrlPatterns("/*");
        casFilterChainProxyFilter.setOrder(1);
        return casFilterChainProxyFilter;
    }
}
