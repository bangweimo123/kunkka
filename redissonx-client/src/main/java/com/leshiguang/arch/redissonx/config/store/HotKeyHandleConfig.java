package com.leshiguang.arch.redissonx.config.store;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-16 13:15
 * @Modify 热点key处理方式
 */
public class HotKeyHandleConfig implements Serializable {
    private String handleMethod;

    private boolean isEnable;

    private Map<String, Object> _properties;

    public String getHandleMethod() {
        return handleMethod;
    }

    public void setHandleMethod(String handleMethod) {
        this.handleMethod = handleMethod;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public Map<String, Object> get_properties() {
        return _properties;
    }

    public void set_properties(Map<String, Object> _properties) {
        this._properties = _properties;
    }
}
