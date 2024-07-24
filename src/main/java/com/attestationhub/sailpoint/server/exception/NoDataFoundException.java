package com.attestationhub.sailpoint.server.exception;

import org.springframework.http.HttpStatus;

public class NoDataFoundException extends AttestationHubServerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -249075671591333035L;

	public NoDataFoundException() {
		super();
	}

	public NoDataFoundException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public NoDataFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoDataFoundException(String message, Throwable cause) {
		super(HttpStatus.NO_CONTENT.value(),message);
	}

	public NoDataFoundException(String message) {
		super(HttpStatus.NO_CONTENT.value(),  message);
	}

	public NoDataFoundException(Throwable cause) {
		super(cause);
	}
	
	
	

}
