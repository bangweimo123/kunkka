package com.leshiguang.arch.kunkka.web.service.impl;

import com.leshiguang.arch.kunkka.db.entity.gen.OperateLog;
import com.leshiguang.arch.kunkka.db.entity.gen.OperateLogCondition;
import com.leshiguang.arch.kunkka.db.mapper.gen.OperateLogMapper;
import com.leshiguang.arch.kunkka.web.operate.log.OperateLogQueryReq;
import com.leshiguang.arch.kunkka.web.service.OperateLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2021-01-22 11:27
 * @Description
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Resource
    private OperateLogMapper operateLogMapper;

    @Override
    public Page<OperateLog> load(OperateLogQueryReq request, PageRequest page) {
        OperateLogCondition condition = new OperateLogCondition();
        condition.setMysqlOffset(Long.valueOf(page.getOffset()).intValue());
        condition.setMysqlLength(page.getPageSize());
        buildCriteria(condition.createCriteria(), request);
        long counts = operateLogMapper.countByCondition(condition);
        Page<OperateLog> result = null;
        if (counts > 0) {
            List<OperateLog> operateLogList = operateLogMapper.selectByCondition(condition);
            result = new PageImpl<>(operateLogList, page, counts);
        } else {
            result = new PageImpl<>(new ArrayList<>(), page, counts);
        }
        return result;
    }

    private void buildCriteria(OperateLogCondition.Criteria criteria, OperateLogQueryReq request) {
        criteria.andRelTypeEqualTo(request.getType()).andRelKeyEqualTo(request.getData());
    }
}

