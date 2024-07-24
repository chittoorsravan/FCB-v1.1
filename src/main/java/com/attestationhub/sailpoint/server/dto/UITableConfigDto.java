package com.attestationhub.sailpoint.server.dto;

import lombok.Data;

@Data
public class UITableConfigDto {
	
	private String gridName;

	private String colName;
	
	private String colHeader;
	
	private String colIndex;
	
	private String colDesc;
	
	private Integer colOrder;
	
	private Integer colWidth;
	
	private String colAlign;
	
	private String colDataType;
	
	private String colType; //Text,Dropdown,selectable
	
	private Boolean hidden;
	
	private Boolean key;
	
	private Boolean editable;
	
	private String editType;
	
	private String editOptions;
	
	private String formatter;
	
	private String formatterOptions;
	
	private Boolean sortable;
	
	private String createdBy;
	
	private Boolean searchable;
	
}
