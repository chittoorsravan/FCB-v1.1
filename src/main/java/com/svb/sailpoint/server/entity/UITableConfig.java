package com.svb.sailpoint.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "uitable_config")
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
public class UITableConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "gridName")
	private String gridName;

	@Column(name = "colName")
	private String colName;
	
	@Column(name = "colHeader")
	private String colHeader;
	
	@Column(name = "colIndex")
	private String colIndex;
	
	@Column(name = "colDesc")
	private String colDesc;
	
	@Column(name = "colOrder")
	private Integer colOrder;
	
	@Column(name = "colWidth")
	private Integer colWidth;
	
	@Column(name = "colAlign")
	private String colAlign;
	
	@Column(name = "colDataType")
	private String colDataType;
	
	@Column(name = "colType")
	private String colType; //Text,Dropdown,selectable
	
	@Column(name = "colHidden")
	private Boolean hidden;
	
	@Column(name = "colKey")
	private Boolean key;
	
	@Column(name = "colEditable")
	private Boolean editable;
	
	@Column(name = "colEditType")
	private String editType;
	
	@Column(name = "colEditOptions")
	private String editOptions;
	
	@Column(name = "colFormatter")
	private String formatter;
	
	@Column(name = "colFormatterOptions")
	private String formatterOptions;
	
	@Column(name = "colSortable")
	private Boolean sortable;
	
	@Column(name = "createdBy")
	private String createdBy;
	
	@EqualsAndHashCode.Exclude
	@Temporal(TemporalType.DATE)
	@Column(name = "createdOn", length = 23)
	private Date createdOn;
	
	@Column(name = "updatedBy")
	private String updatedBy;
	
	@EqualsAndHashCode.Exclude
	@Temporal(TemporalType.DATE)
	@Column(name = "updatedOn", length = 23)
	private Date updatedOn;
	
	@Column(name = "colSearchable")
	private Boolean searchable;
	
	
	

}
