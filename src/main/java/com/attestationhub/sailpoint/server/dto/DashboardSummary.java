package com.attestationhub.sailpoint.server.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DashboardSummary {
	
	private Integer completed;
	
	private Integer pending;
	
	private Integer autoclosed;
	
	private List<Map<String, String>> top5items;
	

}
