package com.svb.sailpoint.server.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAttributes<T> {
	
	private Map<String, List<T>> responseMap;

}
