package com.attestationhub.sailpoint.server.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SummaryResponseMap {
	
	private int completed=0;
    private List<String> list=new ArrayList<>();
    private int pending=0;
    private int closed=0;
    
    @Override
    public String toString() {
        return "ResponseMap{" +
                "completed=" + completed +
                ", list=" + list +
                ", pending=" + pending +
                 ", closed=" + closed +
                '}';
    }

}
