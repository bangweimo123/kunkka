package com.leshiguang.arch.kunkka.demo.client;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-26 18:04
 * @Description
 */
@Configuration
@EnableScheduling
@EnableApolloConfig
@SpringBootApplication(scanBasePackages = {"com.leshiguang.arch.kunkka"}, exclude = {TaskExecutionAutoConfiguration.class, // 禁用定时任务
})
public class KunkkaDemoSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(KunkkaDemoSpringBootApplication.class, args);
    }

}
