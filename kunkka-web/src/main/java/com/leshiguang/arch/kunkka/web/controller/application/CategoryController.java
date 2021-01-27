package com.leshiguang.arch.kunkka.web.controller.application;

import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.kunkka.web.domain.category.*;
import com.leshiguang.arch.kunkka.web.service.CategoryService;
import com.leshiguang.arch.kunkka.web.service.RedisKeyService;
import com.leshiguang.scaffold.api.response.ResultData;
import com.leshiguang.scaffold.api.response.ResultDataBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:17
 * @Modify
 */
@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController extends ApiController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisKeyService redisKeyService;

    @PostMapping("save")
    public ResultData save(@RequestBody CategoryVO category) {
        return ResultDataBuilder.success(categoryService.save(category, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("delete")
    public ResultData delete(@RequestParam("categoryId") Integer categoryId) {
        return ResultDataBuilder.success(categoryService.delete(categoryId, userInfoService.fetchLoginUser().getUserId()));
    }


    @GetMapping("hardDelete")
    public ResultData hardDelete(@RequestParam("categoryId") Integer categoryId) {
        return ResultDataBuilder.success(categoryService.hardDelete(categoryId, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("reset")
    public ResultData reset(@RequestParam("categoryId") Integer categoryId) {
        return ResultDataBuilder.success(categoryService.reset(categoryId, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("publish")
    public ResultData publish(@RequestParam("categoryId") Integer categoryId) {
        return ResultDataBuilder.success(categoryService.publish(categoryId, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("offline")
    public ResultData offline(@RequestParam("categoryId") Integer categoryId) {
        return ResultDataBuilder.success(categoryService.offline(categoryId, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("load")
    public ResultData load(@RequestParam("clusterName") String clusterName, @RequestParam("category") String category) {
        return ResultDataBuilder.success(categoryService.load(clusterName, category, userInfoService.fetchLoginUser().getUserId()));
    }

    @PostMapping("query")
    public ResultData query(@RequestBody CategoryQueryReq request) {
        PageRequest pageRequest = parsePageRequest(request.getPaging());
        return ResultDataBuilder.success(categoryService.query(request, pageRequest));
    }

    @PostMapping("scan")
    public ResultData scan(@RequestBody CategoryScanReq scanReq) {
        return ResultDataBuilder.success(redisKeyService.scan(scanReq));
    }

    @PostMapping("kvGet")
    public ResultData kvGet(@RequestBody CategoryKVReq kvReq) {
        return ResultDataBuilder.success(redisKeyService.kvGet(kvReq));
    }

    @PostMapping("kvSave")
    public ResultData kvSave(@RequestBody CategoryKVSaveReq kvReq) {
        return ResultDataBuilder.success(redisKeyService.kvSave(kvReq));
    }

    @PostMapping("kvDelete")
    public ResultData kvDelete(@RequestBody CategoryKVReq kvReq) {
        return ResultDataBuilder.success(redisKeyService.kvDelete(kvReq));
    }
}
