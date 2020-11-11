package com.leshiguang.arch.redissonx.config.auth;

import com.leshiguang.arch.common.util.AppUtil;
import com.leshiguang.redissonx.common.enums.StrategyOperate;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-11 16:28
 * @Description
 */
public class ApplicationAuthStrategy implements InitAuthStrategy {
    private List<String> applicationList;

    private StrategyOperate operate;

    private Boolean result;

    public ApplicationAuthStrategy(List<String> applicationList, StrategyOperate operate) {
        this.applicationList = applicationList;
        this.operate = operate;
        this.result = init();
    }

    private Boolean init() {
        String currentApplication = AppUtil.appName();
        switch (operate) {
            case in:
                return applicationList.contains(currentApplication);
            case notIn:
                return !applicationList.contains(currentApplication);
        }
        return false;
    }

    @Override
    public boolean initAuthorize() {
        return result;
    }
}
