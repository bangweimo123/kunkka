package com.leshiguang.arch.kunkka.demo.client;

import com.leshiguang.arch.kunkka.client.spring.KunkkaClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author bangwei.mo
 * @Date 2021-01-26 18:06
 * @Description
 */
@Configuration
public class KunkkaConfiguration {
    @Bean
    public KunkkaClientFactory chaosKunkkaClient() {
        return new KunkkaClientFactory("chaos");
    }
}
