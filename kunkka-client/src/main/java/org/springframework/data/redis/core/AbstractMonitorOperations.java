package org.springframework.data.redis.core;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.arch.kunkka.client.exception.KunkkaTimeoutException;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-12 11:39
 * @Description
 */
public abstract class AbstractMonitorOperations {

    protected interface IMC<T> {
        T execute();
    }

    protected interface IMCVoid {
        void execute();
    }

    protected class MonitorCommand {
        private MonitorMethod method;

        private CategoryConfig categoryConfig;

        public MonitorCommand(MonitorMethod method, CategoryConfig categoryConfig) {
            this.method = method;
            this.categoryConfig = categoryConfig;
        }

        public void execute(StoreKey storeKey, IMCVoid imcVoid) throws KunkkaException {
            Transaction t = null;
            try {
                t = Cat.newTransaction("Kunkka." + categoryConfig.getcType(), categoryConfig.getCategory() + ":" + method.getName());
                processTransaction(t, method, storeKey);
                imcVoid.execute();
                t.setSuccessStatus();
            } catch (Throwable e) {
                KunkkaException exception = handleException(e);
                if (t != null) {
                    t.setStatus(e);
                }
                throw exception;
            } finally {
                if (t != null) {
                    t.complete();
                }
            }
        }

        public <T> T execute(StoreKey storeKey, IMC<T> imc) throws KunkkaException {
            Transaction t = null;
            try {
                t = Cat.newTransaction("Kunkka." + categoryConfig.getcType(), categoryConfig.getCategory() + ":" + method.getName());
                processTransaction(t, method, storeKey);
                T result = imc.execute();
                t.setSuccessStatus();
                return result;
            } catch (Throwable e) {
                KunkkaException exception = handleException(e);
                if (t != null) {
                    t.setStatus(e);
                }
                throw exception;
            } finally {
                if (t != null) {
                    t.complete();
                }
            }

        }

        private void processTransaction(Transaction t, MonitorMethod method, StoreKey storeKey) {
//            t.addData("finalKey", categoryConfig.getFinalKey(storeKey));
            if (method.isMultiable()) {
                t.addData("size", method.getBatchCount());
            }
            if (method.isExpired()) {
                t.addData("expire", method.getExpire());
            }
        }

        protected KunkkaException handleException(Throwable e) {
            KunkkaException exception;

            if (e instanceof TimeoutException) {
                exception = new KunkkaTimeoutException(e);
            } else if (e instanceof KunkkaTimeoutException) {
                exception = (KunkkaTimeoutException) e;
            } else if (e instanceof KunkkaException) {
                exception = (KunkkaException) e;
            } else {
                exception = new KunkkaException(e);
            }
            Cat.logError(exception);

            return exception;
        }

    }

    protected static class MonitorMethod {

        private String name;

        private Long expire = -1l;

        private boolean expired = false;

        private Integer batchCount = 1;

        private MonitorMethod(String name) {
            this.name = name;
        }

        public static MonitorMethod create(String name) {
            MonitorMethod method = new MonitorMethod(name);
            return method;
        }

        public MonitorMethod setExpire(Long expire, TimeUnit timeUnit) {
            this.expired = true;
            this.expire = timeUnit.toSeconds(expire);
            return this;
        }

        public MonitorMethod setExpire(Date date) {
            this.expired = true;
            this.expire = (date.getTime() - new Date().getTime()) / 1000;
            return this;
        }

        public MonitorMethod setBatch(Integer size) {
            if (size != null) {
                this.batchCount = size;
            }
            return this;
        }

        public MonitorMethod setBatch(Long size) {
            if (size != null) {
                this.batchCount = size.intValue();
            }
            return this;
        }


        public String getName() {
            return name;
        }

        public Long getExpire() {
            return expire;
        }

        public boolean isExpired() {
            return expired;
        }

        public Boolean isMultiable() {
            return null != batchCount && batchCount > 1;
        }

        public Integer getBatchCount() {
            return batchCount;
        }
    }
}
