package com.svb.sailpoint.server.controller;

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

import com.svb.sailpoint.server.dto.SailpointRequest;
import com.svb.sailpoint.server.dto.SailpointResponse;
import com.svb.sailpoint.server.entity.Entitlement;
import com.svb.sailpoint.server.service.EntitlementAttestationService;
import com.svb.sailpoint.server.utils.ApplicationTypes;
import com.svb.sailpoint.server.utils.Attribute;
import com.svb.sailpoint.server.utils.RequestorTypes;
import com.svb.sailpoint.server.utils.ValueTypes;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/entitlement")
@Slf4j
@Tag(name = "Entitlement Attestation Service", description = "API to perform action on Entitlement Attestation Data")
public class EntitlementAttestationController {
	
	@Autowired
	private EntitlementAttestationService entitlementAttestationService;
	
	
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
			entitlementAttestationService.saveAttestationDataFor(username,attestationName,rows,attribute,applicationTypes,requestorTypes,valueTypes);		
			return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@PostMapping(value="/get",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEntitlementsForUser(HttpServletRequest httpServletRequest,@RequestBody SailpointRequest<Entitlement> sailpointRequest) throws Exception{
			log.info("Request received to get Entitlements for  {} ",sailpointRequest.getWorkflowArgs());
			SailpointResponse response = entitlementAttestationService.getEntitlementAttestations(sailpointRequest);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateApplicationForUser(HttpServletRequest httpServletRequest,@RequestBody SailpointRequest<Entitlement> sailpointRequest) throws Exception{
			log.info("Request received to get Entitlements for  {} ",sailpointRequest.getWorkflowArgs());
			SailpointResponse response = entitlementAttestationService.updateEntitlementAttestations(sailpointRequest);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PostMapping(value="/get/favorite",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveFavorite(HttpServletRequest httpServletRequest,@RequestBody Entitlement entitlement,@RequestParam String owner) throws Exception{
			SailpointResponse response = entitlementAttestationService.setFavorites(entitlement,owner);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

}
