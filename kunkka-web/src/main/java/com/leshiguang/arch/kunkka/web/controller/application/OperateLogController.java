package com.leshiguang.arch.kunkka.web.controller.application;

import com.leshiguang.arch.kunkka.web.operate.log.OperateLogQueryReq;
import com.leshiguang.arch.kunkka.web.service.OperateLogService;
import com.leshiguang.scaffold.api.response.ResultData;
import com.leshiguang.scaffold.api.response.ResultDataBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-22 11:34
 * @Description
 */
@RestController
@RequestMapping("/api/log")
@Slf4j
public class OperateLogController extends ApiController {
    @Resource
    private OperateLogService operateLogService;

    @PostMapping("load")
    public ResultData reset(@RequestBody OperateLogQueryReq queryReq) {
        PageRequest pageRequest = parsePageRequest(queryReq.getPaging());
        return ResultDataBuilder.success(operateLogService.load(queryReq, pageRequest));
    }
}
