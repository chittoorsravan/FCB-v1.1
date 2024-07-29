package com.attestationhub.sailpoint.server.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SummaryResponseMap {
	
	private int completed=0;
    private List<Map<String,String>> top5items=new ArrayList<Map<String,String>>();
    private int pending=0;
    private int autoClosed=0;
    
    @Override
    public String toString() {
        return "ResponseMap{" +
                "completed=" + completed +
                ", top5items=" + top5items +
                ", pending=" + pending +
                 ", autoClosed=" + autoClosed +
                '}';
    }

}
