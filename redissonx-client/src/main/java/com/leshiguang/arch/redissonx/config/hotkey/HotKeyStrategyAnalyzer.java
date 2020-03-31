package com.leshiguang.arch.redissonx.config.hotkey;

import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.category.HotKeyStrategyBO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.objenesis.instantiator.util.ClassUtils;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-30 17:29
 * @Modify
 */
public class HotKeyStrategyAnalyzer {

    public static void analyze(StoreCategoryConfig storeCategoryConfig, CategoryBO categoryBO) {
        //默认值
        if (CollectionUtils.isEmpty(categoryBO.getHotKeyStrategyList())) {
            HotKeyStrategy defaultStrategy = new LocalCacheHotKeyStrategy();
            defaultStrategy.process();
            storeCategoryConfig.addHotKeyStrategy(defaultStrategy);
        } else {
            for (HotKeyStrategyBO hotKeyStrategyBO : categoryBO.getHotKeyStrategyList()) {
                Class<HotKeyStrategy> strategyClazz = HotKeyStrategyEnum.getClazz(hotKeyStrategyBO.getStrategy());
                if (null != strategyClazz) {
                    HotKeyStrategy hotKeyStrategy = ClassUtils.newInstance(strategyClazz);
                    hotKeyStrategy.parse(hotKeyStrategyBO);
                    hotKeyStrategy.process();
                    storeCategoryConfig.addHotKeyStrategy(hotKeyStrategy);
                }
            }
        }
    }
}
