package com.leshiguang.arch.kunkka.web.controller.application;

import com.leshiguang.arch.cas.support.service.UserInfoService;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterAuthVO;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterConnectVO;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterQueryReq;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterVO;
import com.leshiguang.arch.kunkka.web.service.ClusterService;
import com.leshiguang.scaffold.api.response.ResultData;
import com.leshiguang.scaffold.api.response.ResultDataBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo
 * @Date 2020-03-20 14:53
 * @Modify
 */
@RestController
@RequestMapping("/api/cluster")
@Slf4j
public class ClusterController extends ApiController {
    @Resource
    private ClusterService clusterService;
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("save")
    public ResultData save(@RequestBody ClusterVO cluster) {
        return ResultDataBuilder.success(clusterService.save(cluster, userInfoService.fetchLoginUser().getUserId()));
    }

    @PostMapping("onlineSave")
    public ResultData onlineSave(@RequestBody ClusterVO cluster) {
        return ResultDataBuilder.success(clusterService.onlineSave(cluster, userInfoService.fetchLoginUser().getUserId()));
    }

    @PostMapping("saveConnect")
    public ResultData saveClusterConnect(@RequestBody ClusterConnectVO clusterConnect) {
        return ResultDataBuilder.success(clusterService.saveConnect(clusterConnect, userInfoService.fetchLoginUser().getUserId()));
    }


    @GetMapping("loadConnect")
    public ResultData loadClusterConnect(@RequestParam String clusterName, @RequestParam String region) {
        return ResultDataBuilder.success(clusterService.loadConnect(clusterName, region));
    }

    @PostMapping("saveAuth")
    public ResultData saveClusterAuth(@RequestBody ClusterAuthVO clusterAuth) {
        return ResultDataBuilder.success(clusterService.saveAuth(clusterAuth, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("loadAuths")
    public ResultData loadClusterAuths(@RequestParam String clusterName) {
        return ResultDataBuilder.success(clusterService.loadAuths(clusterName));
    }

    @GetMapping("deleteAuth")
    public ResultData deleteClusterAuth(@RequestParam Integer authId) {
        return ResultDataBuilder.success(clusterService.deleteAuth(authId));
    }

    @GetMapping("delete")
    public ResultData delete(@RequestParam String clusterName) {
        return ResultDataBuilder.success(clusterService.delete(clusterName, userInfoService.fetchLoginUser().getUserId()));
    }

    @PostMapping("query")
    public ResultData query(@RequestBody ClusterQueryReq request) {
        PageRequest pageRequest = parsePageRequest(request.getPaging());
        return ResultDataBuilder.success(clusterService.query(request, pageRequest));
    }

    @GetMapping("load")
    public ResultData load(@RequestParam String clusterName) {
        return ResultDataBuilder.success(clusterService.load(clusterName));
    }

    @GetMapping("publish")
    public ResultData publish(@RequestParam String clusterName) {
        return ResultDataBuilder.success(clusterService.publish(clusterName, userInfoService.fetchLoginUser().getUserId(), false));
    }


    @GetMapping("rePublish")
    public ResultData rePublish(@RequestParam String clusterName) {
        return ResultDataBuilder.success(clusterService.publish(clusterName, userInfoService.fetchLoginUser().getUserId(), true));
    }

    @GetMapping("offline")
    public ResultData offline(@RequestParam String clusterName) {
        return ResultDataBuilder.success(clusterService.offline(clusterName, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("hardDelete")
    public ResultData hardDelete(@RequestParam String clusterName) {
        return ResultDataBuilder.success(clusterService.hardDelete(clusterName, userInfoService.fetchLoginUser().getUserId()));
    }

    @GetMapping("reset")
    public ResultData reset(@RequestParam String clusterName) {
        return ResultDataBuilder.success(clusterService.reset(clusterName, userInfoService.fetchLoginUser().getUserId()));
    }
}
