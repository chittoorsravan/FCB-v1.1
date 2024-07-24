package com.svb.sailpoint.server.dto;

import lombok.Data;

@Data
public class SailpointResponse {
	
	
	@SuppressWarnings("rawtypes")
	private ResponseAttributes attributes;
	
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
