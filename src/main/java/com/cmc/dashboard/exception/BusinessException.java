package com.cmc.dashboard.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7383897452576766368L;

	public BusinessException() {
		super();
	}

	public BusinessException(final String message) {
		super(message);
	}

	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BusinessException(final Throwable cause) {
		super(cause);
	}

}
