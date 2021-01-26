package com.leshiguang.arch.kunkka.web.controller.application;

import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.kunkka.web.domain.connect.ConnectQueryReq;
import com.leshiguang.arch.kunkka.web.domain.connect.ConnectVO;
import com.leshiguang.arch.kunkka.web.service.ConnectService;
import com.leshiguang.scaffold.api.response.ResultData;
import com.leshiguang.scaffold.api.response.ResultDataBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 14:14
 * @Modify
 */
@RestController
@RequestMapping("/api/connect")
@Slf4j
public class ConnectController extends ApiController {
    @Resource
    private ConnectService connectService;
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("save")
    public ResultData save(@RequestBody ConnectVO connect) {
        return ResultDataBuilder.success(connectService.save(connect, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("delete")
    public ResultData delete(@RequestParam String connectName) {
        return ResultDataBuilder.success(connectService.delete(connectName, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("hardDelete")
    public ResultData hardDelete(@RequestParam String connectName) {
        return ResultDataBuilder.success(connectService.hardDelete(connectName, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("reset")
    public ResultData reset(@RequestParam String connectName) {
        return ResultDataBuilder.success(connectService.reset(connectName, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("loadRelationClusters")
    public ResultData loadRelationClusters(@RequestParam String connectName) {
        return ResultDataBuilder.success(connectService.loadRelationClusters(connectName));
    }

    @PostMapping("query")
    public ResultData query(@RequestBody ConnectQueryReq request) {
        PageRequest pageRequest = parsePageRequest(request.getPaging());
        return ResultDataBuilder.success(connectService.query(request, pageRequest));
    }
}
