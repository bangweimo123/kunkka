package com.leshiguang.arch.redissonx.server.controller.application;

import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.redissonx.server.domain.category.CategoryQueryReq;
import com.leshiguang.arch.redissonx.server.domain.rediskey.MValueBO;
import com.leshiguang.arch.redissonx.server.domain.rediskey.RedisKeyValueSaveRequest;
import com.leshiguang.arch.redissonx.server.service.CategoryService;
import com.leshiguang.arch.redissonx.server.service.RedisKeyService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.request.CategoryQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:17
 * @Modify
 */
@RestController
@Slf4j
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisKeyService redisKeyService;

    @PostMapping("/api/category/save/{clusterName}")
    public RedissonxResponse save(@PathVariable String clusterName, @RequestBody CategoryBO category) {
        return categoryService.save(clusterName, category, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/category/delete/{clusterName}")
    public RedissonxResponse delete(@PathVariable String clusterName, @RequestParam("category") String category) {
        return categoryService.delete(clusterName, category, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/category/publish/{clusterName}")
    public RedissonxResponse publish(@PathVariable String clusterName, @RequestParam("category") String category, @RequestParam("version") String version) {
        return categoryService.publish(clusterName, category, version, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/category/offline/{clusterName}")
    public RedissonxResponse offline(@PathVariable String clusterName, @RequestParam("category") String category) {
        return categoryService.offline(clusterName, category, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/category/get/{clusterName}")
    public RedissonxResponse get(@PathVariable String clusterName, @RequestParam("category") String category) {
        return categoryService.get(clusterName, category);
    }

    @PostMapping("/api/category/query/{clusterName}")
    public RedissonxResponse query(@PathVariable String clusterName, @RequestBody CategoryQueryReq request) {
        CategoryQueryRequest queryRequest = new CategoryQueryRequest();
        queryRequest.setClusterName(clusterName);
        queryRequest.setUserId(request.getUserId());
        queryRequest.setKeyword(request.getKeyword());
        queryRequest.setShowHotKey(request.getShowHotKey());
        queryRequest.setStatusList(request.getStatusList());
        return categoryService.query(clusterName, queryRequest, request.getPaging());
    }

    @GetMapping("/api/category/scan/{clusterName}")
    public RedissonxResponse scan(@PathVariable String clusterName, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "tenantId", required = false) Integer tenantId, @RequestParam(value = "paramFormat", required = false) String paramFormat) {
        return categoryService.scan(clusterName, category, paramFormat, tenantId, userInfoService.fetchLoginUser().getUserId());
    }

    @GetMapping("/api/category/keyvalue/{clusterName}")
    public RedissonxResponse keyvalue(@PathVariable String clusterName, @RequestParam(value = "category") String category, @RequestParam(value = "key") String key) {
        return redisKeyService.keyvalue(clusterName, category, key);
    }

    @PostMapping("/api/category/keyvaluesave/{clusterName}")
    public RedissonxResponse keyvaluesave(@PathVariable String clusterName, @RequestBody RedisKeyValueSaveRequest request) {
        List<String> params = request.getParams();
        String category = request.getCategory();
        MValueBO value = request.getValue();
        String[] paramsData = new String[params.size()];
        for (int i = 0; i < params.size(); i++) {
            paramsData[i] = params.get(i);
        }
        return redisKeyService.keyValueSave(clusterName, category, value.parse(), paramsData);
    }

    @GetMapping("/api/category/keyvaluedelete/{clusterName}")
    public RedissonxResponse keyvaluedelete(@PathVariable String clusterName, @RequestParam(value = "category") String category, @RequestParam(value = "key") String key) {
        return redisKeyService.deleteKey(clusterName, category, key);
    }
}
