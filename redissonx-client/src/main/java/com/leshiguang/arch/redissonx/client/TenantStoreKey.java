package com.leshiguang.arch.redissonx.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-13 12:13
 * @Modify
 */
public class TenantStoreKey extends StoreKey {
    private static final Integer DEFAULT_TENANT = 1;
    private Integer tenantId;

    private TenantStoreKey(String category, Object... params) {
        this(category, DEFAULT_TENANT, category, params);
    }

    public TenantStoreKey(String category, Integer tenantId, Object... params) {
        super(category, params);
        this.tenantId = tenantId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(tenantId).
                append(category).append(params).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tenantId).
                append(category).append(params).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TenantStoreKey) {
            TenantStoreKey sk = (TenantStoreKey) obj;
            return new EqualsBuilder().
                    append(tenantId, sk.tenantId).
                    append(category, sk.category).
                    append(params, sk.params).
                    isEquals();
        }
        return false;
    }
}
