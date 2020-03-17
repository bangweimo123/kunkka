package com.leshiguang.arch.redissonx.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-02-11 18:56
 * @Modify
 */
public class StoreKey implements Serializable {

    /**
     * Item category
     */
    protected String category;

    /**
     * Parameters
     */
    protected Object[] params;

    /**
     * Constructor
     */
    public StoreKey(String category, Object... params) {
        this.category = category;
        this.params = params;
    }

    public static StoreKey valueOf(String category, Object... params) {
        return new StoreKey(category, params);
    }

    public String getCategory() {
        return category;
    }

    /**
     * @return the params
     */
    public Object[] getParams() {
        return params;
    }

    public List<Object> getParamsAsList() {
        if (params == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(params);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                append(category).append(params).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(category).append(params).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StoreKey) {
            StoreKey sk = (StoreKey) obj;
            return new EqualsBuilder().
                    append(category, sk.category).
                    append(params, sk.params).
                    isEquals();
        }
        return false;
    }
}

