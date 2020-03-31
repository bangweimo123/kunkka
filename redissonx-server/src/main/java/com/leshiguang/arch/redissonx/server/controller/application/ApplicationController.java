package com.leshiguang.arch.redissonx.server.controller.application;

import com.leshiguang.redissonx.common.base.RedissonxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-30 18:37
 * @Modify
 */
@RestController
@Slf4j
public class ApplicationController {
    @Resource
    private ApplicationService applicationService;

    @PostMapping("/api/application/save/{clusterName}")
    public RedissonxResponse save(@PathVariable String clusterName, @RequestBody ApplicationBO application) {
        return applicationService.save(clusterName, category, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/application/delete/{clusterName}")
    public RedissonxResponse delete(@PathVariable String clusterName, @RequestParam(name = "application") String application) {
        return applicationService.delete(clusterName, application);
    }

    @GetMapping("/api/application/enable/{clusterName}")
    public RedissonxResponse enable(@PathVariable String clusterName, @RequestParam(name = "application") String application) {
        return applicationService.delete(clusterName, application);
    }

    @GetMapping("/api/application/disable/{clusterName}")
    public RedissonxResponse enable(@PathVariable String clusterName, @RequestParam(name = "application") String application) {
        return applicationService.delete(clusterName, application);
    }

    @GetMapping("/api/application/modify/{clusterName}")
    public RedissonxResponse enable(@PathVariable String clusterName, @RequestParam(name = "application") String application) {
        return applicationService.delete(clusterName, application);
    }
}
