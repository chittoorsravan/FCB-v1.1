package com.attestationhub.sailpoint.server.dto;

import java.util.List;

import com.attestationhub.sailpoint.server.entity.ManageAccess;

import lombok.Data;

@Data
public class SailpointUserAccessResponse {
	
	private List<SailpointUsers> sailpointUsers;
	
	private List<ManageAccess> manageAccessList;
	
	private String requestor;
	
	private String status;

}
