package com.svb.sailpoint.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.svb.sailpoint.server.utils.DateSerializer;
import com.svb.sailpoint.server.utils.ForceStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ManageAccess")
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
public class ManageAccess {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@JsonSerialize(using = ForceStringSerializer.class)
	private Integer id;
	
	@Column(name = "entowner")
	private String entowner;
	
	@Column(name = "entname")
	private String entname;
	
	@Column(name = "displayname")
	private String displayname;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "requester")
	private String requester;
	
	@Column(name = "requestedFor")
	private String requestedFor;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "signOffStatus")
	private String signoffstatus;
	
	@Column(name = "completionComments")
	private String completioncomments;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "created")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date created;
}
