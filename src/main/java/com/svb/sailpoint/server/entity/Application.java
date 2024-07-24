
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

	@Column(name = "application")
	private String application;

	@Column(name = "type")
	private String type;

	@Column(name = "attribute")
	private String attribute;

	@Column(name = "appValue")
	private String value;

	@Column(name = "displayName")
	private String display_name;

	@Column(name = "signOffStatus")
	private String signoffstatus;

	@Column(name = "action")
	private String action;

	@Column(name = "completionComments")
	private String completioncomments;

	@Column(name = "requester")
	private String requester;

	@EqualsAndHashCode.Exclude
	@Column(name = "created")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date created;

	@EqualsAndHashCode.Exclude
	@Column(name = "signOffDate")
	@CreationTimestamp
	@JsonSerialize(using = DateSerializer.class)
	private Date signoffdate;
	
}
