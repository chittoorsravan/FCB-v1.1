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
import com.svb.sailpoint.server.entity.RAttestation;
import com.svb.sailpoint.server.service.RAttestationService;
import com.svb.sailpoint.server.utils.ApplicationTypes;
import com.svb.sailpoint.server.utils.Attribute;
import com.svb.sailpoint.server.utils.RequestorTypes;
import com.svb.sailpoint.server.utils.ValueTypes;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rattestation")
@Slf4j
@Tag(name = "_R Attestation Service", description = "API to perform action on _R Attestation Data")
public class RAttestationController {
	
	@Autowired
	private RAttestationService rAttestationService;	
	
	@PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestParam String username,
					@RequestParam String attestationName,
					@RequestParam String accessid,
					@RequestParam Integer rows, 
					@RequestParam Attribute attribute,
					@RequestParam ApplicationTypes applicationTypes,
					@RequestParam RequestorTypes requestorTypes,
					@RequestParam ValueTypes valueTypes,
					@RequestParam Boolean commonaccess,
					HttpServletRequest httpServletRequest) throws Exception{
			log.info("Request received to create a _R Attestations with name {} ",username);
			rAttestationService.saveAttestationDataFor(username,attestationName,accessid,rows,attribute,applicationTypes,requestorTypes,valueTypes,commonaccess);		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	@PostMapping(value="/get",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRAttestationForUser(HttpServletRequest httpServletRequest,@RequestBody SailpointRequest<RAttestation> sailpointRequest) throws Exception{
			log.info("Request received to get _R for  {} ",sailpointRequest.getWorkflowArgs());
			SailpointResponse response = rAttestationService.getRAttestations(sailpointRequest);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRAttestationsForUser(HttpServletRequest httpServletRequest,@RequestBody SailpointRequest<RAttestation> sailpointRequest) throws Exception{
			log.info("Request received to get _R for  {} ",sailpointRequest);
			SailpointResponse response = rAttestationService.updateRAttestations(sailpointRequest);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
