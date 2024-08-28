package com.attestationhub.sailpoint.server.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WorkflowArgs<T> {
	
	private String owner;
	
	private String attestationType;
	
	private List<T> itemsList;
	
	private  Map<String,T> items;
	
	private String comments;
	
}
