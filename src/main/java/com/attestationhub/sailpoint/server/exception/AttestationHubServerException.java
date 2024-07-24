package com.attestationhub.sailpoint.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AttestationHubServerException extends RuntimeException{

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

	public AttestationHubServerException(int errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public AttestationHubServerException() {
		super();
	}

	public AttestationHubServerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	

	public AttestationHubServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public AttestationHubServerException(String message) {
		super(message);
	}

	public AttestationHubServerException(Throwable cause) {
		super(cause);
	}
	
	
	

}
