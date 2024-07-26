package com.attestationhub.sailpoint.server.dto;

import lombok.Data;

@Data
public class SailpointResponse<T> {
	
	private T attributes;
	
	private Boolean complete;
	
	private String errors;
	
	private Boolean failure;
	
	private String metaData;
	
	private String requestID;
	
	private Boolean retry;
	
	private Integer retryWait;
	
	private String status;
	
	private Boolean success;
	
	private String warnings;
	
	private long responsetime;
	
}
