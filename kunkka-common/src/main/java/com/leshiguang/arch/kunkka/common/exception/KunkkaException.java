package com.leshiguang.arch.kunkka.common.exception;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-11 14:30
 * @Description
 */
public class KunkkaException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1l;

    private ErrorCode errorCode;

    private static ErrorCode defaultErroCode = new ErrorCode() {
        @Override
        public int getCode() {
            return 500;
        }

        @Override
        public String getMsg() {
            return "系统错误";
        }
    };

    public KunkkaException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public KunkkaException(ErrorCode errorCode, String... params) {
        super(String.format(errorCode.getMsg(), params));
        this.errorCode = errorCode;
    }

    public KunkkaException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMsg(), cause);
        this.errorCode = errorCode;
    }

    public KunkkaException(Throwable cause) {
        super(defaultErroCode.getMsg(), cause);
        this.errorCode = defaultErroCode;
    }

    public KunkkaException(Throwable cause, ErrorCode errorCode, String... params) {
        super(String.format(errorCode.getMsg(), params), cause);
        this.errorCode = errorCode;
    }

    public KunkkaException() {
        super(defaultErroCode.getMsg());
        this.errorCode = defaultErroCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
