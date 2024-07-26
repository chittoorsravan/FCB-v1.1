package com.attestationhub.sailpoint.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.attestationhub.sailpoint.server.dto.ResponseAttributes;
import com.attestationhub.sailpoint.server.dto.SailpointRequest;
import com.attestationhub.sailpoint.server.dto.SailpointResponse;
import com.attestationhub.sailpoint.server.dto.SummaryResponseAttributes;
import com.attestationhub.sailpoint.server.entity.Application;
import com.attestationhub.sailpoint.server.service.ApplicationAttestationService;
import com.attestationhub.sailpoint.server.utils.ApplicationTypes;
import com.attestationhub.sailpoint.server.utils.Attribute;
import com.attestationhub.sailpoint.server.utils.RequestorTypes;
import com.attestationhub.sailpoint.server.utils.ValueTypes;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/application")
@Slf4j
@Tag(name = "Application Attestation Service", description = "API to perform action on Application Attestation Data")
public class ApplicationAttestationController {
	
	@Autowired
	private ApplicationAttestationService applicationAttestationService;	
	
	@PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestParam String username,
					@RequestParam String attestationName,
					@RequestParam Integer rows, 
					@RequestParam Attribute attribute,
					@RequestParam ApplicationTypes applicationTypes,
					@RequestParam RequestorTypes requestorTypes,
					@RequestParam ValueTypes valueTypes,
					HttpServletRequest httpServletRequest) throws Exception{
			log.info("Request received to create a customer with name {} ",username);
			applicationAttestationService.saveAttestationDataFor(username,attestationName,rows,attribute,applicationTypes,requestorTypes,valueTypes);		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	@PostMapping(value="/get",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getApplicationsForUser(HttpServletRequest httpServletRequest,@RequestBody SailpointRequest<Application> sailpointRequest) throws Exception{
			log.info("Request received to get Entitlements for  {} ",sailpointRequest.getWorkflowArgs());
			SailpointResponse<ResponseAttributes<Application>> response = applicationAttestationService.getApplicationAttestations(sailpointRequest);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PostMapping(value="/get/summary",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getApplicationsSummaryForUser(HttpServletRequest httpServletRequest,@RequestBody SailpointRequest<Application> sailpointRequest,@RequestParam(required = false) Integer limit) throws Exception{
			log.info("Request received to get Entitlements for  {} ",sailpointRequest.getWorkflowArgs());
			SailpointResponse<SummaryResponseAttributes> response = applicationAttestationService.getApplicationAttestationsSummary(sailpointRequest,limit);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateApplicationForUser(HttpServletRequest httpServletRequest,@RequestBody SailpointRequest<Application> sailpointRequest) throws Exception{
			log.info("Request received to get Entitlements for  {} ",sailpointRequest);
			SailpointResponse<ResponseAttributes<Application>> response = applicationAttestationService.updateApplicationAttestations(sailpointRequest);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

}
