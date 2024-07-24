package com.svb.sailpoint.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SailPointServerException extends RuntimeException{

	private static final long serialVersionUID = 7780388577878106417L;
	
	
	private int errorCode;
	
	private String errorMessage;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public SailPointServerException(int errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public SailPointServerException() {
		super();
	}

	public SailPointServerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	

	public SailPointServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SailPointServerException(String message) {
		super(message);
	}

	public SailPointServerException(Throwable cause) {
		super(cause);
	}
	
	
	

}
