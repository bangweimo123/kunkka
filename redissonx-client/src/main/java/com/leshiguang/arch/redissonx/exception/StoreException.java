package com.leshiguang.arch.redissonx.exception;


public class StoreException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1l;

	public StoreException(Throwable cause) {
		super(cause);
	}

	public StoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public StoreException(String message) {
		super(message);
	}

    public StoreException() {
        super();
    }

}
