package com.leshiguang.arch.kunkka.web.service;

import com.leshiguang.arch.kunkka.db.entity.gen.OperateLog;
import com.leshiguang.arch.kunkka.web.operate.log.OperateLogQueryReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @Author bangwei.mo
 * @Date 2021-01-22 11:25
 * @Description
 */
public interface OperateLogService {
    Page<OperateLog> load(OperateLogQueryReq queryReq, PageRequest pageRequest);
}
