package com.zhengbangnet.common;

/**
 *
 * 操作全局异常
 *
 */
public class HttpException extends Exception {

	private static final long serialVersionUID = -7510494037942736287L;

	public HttpException(String msg) {
        super(msg);
    }

    public HttpException(Exception cause) {
        super(cause);
    }

    public HttpException(String msg, Exception cause) {
        super(msg, cause);
    }
}
