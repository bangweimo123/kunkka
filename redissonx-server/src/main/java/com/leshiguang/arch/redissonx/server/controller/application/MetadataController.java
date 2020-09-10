package com.leshiguang.arch.redissonx.server.controller.application;

import com.leshiguang.arch.common.api.response.ResultData;
import com.leshiguang.cloud.chaos.metadata.api.model.Region;
import com.leshiguang.cloud.chaos.metadata.api.service.RegionService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-03 11:02
 * @Description
 */
@RestController
@Slf4j
public class MetadataController {
    @DubboReference(version = "0.0.1-SNAPSHOT", check = false)
    private RegionService regionService;


    @GetMapping("/api/metadata/region/query")
    public RedissonxResponse regionQuery() {
        ResultData<List<Region>> regions = regionService.queryRegions();
        return RedissonxResponseBuilder.success(regions.getData());
    }
}
