package com.leshiguang.arch.redissonx.server;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.leshiguang.arch.cas.support.springboot.EnableCasClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用启动入口
 *
 * @author lifesense
 */
@Configuration
@EnableScheduling
@EnableApolloConfig({"application", "lx-arch.redissonx"})
@SpringBootApplication(scanBasePackages = {"com.leshiguang.arch.cas", "com.leshiguang.arch.redissonx"}, exclude = {TaskExecutionAutoConfiguration.class, // 禁用定时任务
})
@MapperScan({"com.leshiguang.arch.redissonx.core.mapper.gen", "com.leshiguang.arch.redissonx.core.mapper.ext"})
@EnableCasClient
public class RedissonxSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonxSpringBootApplication.class, args);
    }


}
