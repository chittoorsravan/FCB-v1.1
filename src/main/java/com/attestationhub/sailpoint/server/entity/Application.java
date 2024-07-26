
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
@Table(name = "Application")
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@JsonSerialize(using = ForceStringSerializer.class)
	private Integer id;

	@Column(name = "attestationName")
	private String attestationname;

	@Column(name = "owner")
	private String owner;

	@Column(name = "requester")
	private String requester;

	@Column(name = "application")
	private String application;

	@Column(name = "type")
	private String type;

	@Column(name = "attribute")
	private String attribute;

	@Column(name = "appValue")
	private String value;

	@Column(name = "displayname")
	private String displayname;

	@Column(name = "signOffStatus")
	private String signoffstatus;

	@Column(name = "action")
	private String action;

	@Column(name = "completionComments")
	private String completioncomments;

	@EqualsAndHashCode.Exclude
	@Column(name = "created")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date created;

	@EqualsAndHashCode.Exclude
	@Column(name = "signoffdate")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date signoffdate;

	@EqualsAndHashCode.Exclude
	@Column(name = "decisiondate")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date decisiondate;

	@EqualsAndHashCode.Exclude
	@Column(name = "duedate")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date duedate;

	@Column(name = "description")
	private String description;

	@Column(name = "accessid")
	private String accessid;

	@Column(name = "modified")
	private String modified;

	@Column(name = "workitem")
	private String workitem;

	@Column(name = "businessappname")
	private String businessappname;

}
