package com.attestationhub.sailpoint.server.dto;

import java.util.List;

import lombok.Data;

@Data
public class DashboardSummary {
	
	private Integer completed;
	
	private Integer pending;
	
	private Integer autoclosed;
	
	private List<String> list;
	

}
