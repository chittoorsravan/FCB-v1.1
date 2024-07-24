package com.attestationhub.sailpoint.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.attestationhub.sailpoint.server.utils.DateSerializer;
import com.attestationhub.sailpoint.server.utils.ForceStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RAttestation")
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
public class RAttestation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@JsonSerialize(using = ForceStringSerializer.class)
	private Integer id;

	@Column(name = "attestationname")
	private String attestationname;
	
	@Column(name = "completioncomments")
	private String completioncomments;

	@Column(name = "owner")
	private String owner;
	
	@Column(name = "requester")
	private String requester;
	
	@Column(name = "attestationid")
	private String attestationid;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "accessid")
	private String accessid;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "signoffdate")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date signoffdate;
	
	@Column(name = "application")
	private String application;
	
	@Column(name = "accountname")
	private String accountname;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "displayname")
	private String displayname;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "modified")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date modified;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "decisiondate")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date decisiondate;
	
	@Column(name = "attribute")
	private String attribute;
	
	@Column(name = "signoffstatus")
	private String signoffstatus;
	
	@Column(name = "rValue")
	private String value;
	
	@Column(name = "businessappname")
	private String businessappname;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "created")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date created;
	
	@Column(name = "commonaccess")
    private Boolean commonaccess;

}
