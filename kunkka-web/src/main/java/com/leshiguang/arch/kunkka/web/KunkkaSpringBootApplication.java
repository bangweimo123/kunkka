package com.leshiguang.arch.kunkka.web;

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
@EnableApolloConfig
@SpringBootApplication(scanBasePackages = {"com.leshiguang.arch.kunkka"}, exclude = {TaskExecutionAutoConfiguration.class, // 禁用定时任务
})
@MapperScan({"com.leshiguang.arch.kunkka.db.mapper.gen", "com.leshiguang.arch.kunkka.db.mapper.ext"})
@EnableCasClient
public class KunkkaSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(KunkkaSpringBootApplication.class, args);
    }


}
