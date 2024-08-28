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

import com.attestationhub.sailpoint.server.utils.BooleanToStringSerializer;
import com.attestationhub.sailpoint.server.utils.DateSerializer;
import com.attestationhub.sailpoint.server.utils.ForceStringSerializer;
import com.attestationhub.sailpoint.server.utils.StringToBooleanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Entitlement")
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
public class Entitlement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@JsonSerialize(using = ForceStringSerializer.class)
	private Integer id;
	
	@Column(name = "attestationName")
	private String attestationname;

	@Column(name = "owner")
	private String owner;

	@Column(name = "application")
	private String application;

	@Column(name = "type")
	private String type;
	
	@Column(name = "appValue")
	private String value;

	@Column(name = "attribute")
	private String attribute;

	@Column(name = "displayname",length = 500)
	private String displayname;

	@Column(name = "signoffstatus")
	private String signoffstatus;

	@Column(name = "action")
	private String action;

	@Column(name = "completioncomments")
	private String completioncomments;

	@Column(name = "requester")
	private String requester;

	@EqualsAndHashCode.Exclude
	@Column(name = "created")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date created;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "signoffdate")
	@JsonSerialize(using = DateSerializer.class)
	private Date signoffdate;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "decisiondate")
	@JsonSerialize(using = DateSerializer.class)
	private Date decisiondate;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "duedate")
	@JsonSerialize(using = DateSerializer.class)
	private Date duedate;
	
	@Column(name = "businessappname")
    private String businessappname;
	
	@Column(name = "isprivileged")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
    @JsonSerialize(using = BooleanToStringSerializer.class)
    private Boolean isprivileged;
	
	@Column(name = "iscertifiable")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
    @JsonSerialize(using = BooleanToStringSerializer.class)
	private Boolean iscertifiable;
	
	@Column(name = "isrequestable")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
    @JsonSerialize(using = BooleanToStringSerializer.class)
	private Boolean isrequestable;
	
	@Column(name = "issensitive")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
    @JsonSerialize(using = BooleanToStringSerializer.class)
	private Boolean issensitive;
	
	@Column(name = "admAccessRequired")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
    @JsonSerialize(using = BooleanToStringSerializer.class)
	private Boolean admaccessrequired;
	
	@Column(name = "accessid")
    private String accessid;
	
	@Column(name = "appid")
	private String appid;
	
	@Column(name = "modified")
    private String modified;
	
	@Column(name = "workitem")
    private String workitem;
    
	@Column(name = "description", length = 500)
	private String description;
}
