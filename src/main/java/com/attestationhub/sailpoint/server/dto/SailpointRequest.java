package com.attestationhub.sailpoint.server.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SailpointRequest<T> {
	
	private WorkflowArgs<T> workflowArgs;

}
