package com.attestationhub.sailpoint.server.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.attestationhub.sailpoint.server.dto.SailpointUserAccessResponse;
import com.attestationhub.sailpoint.server.entity.ManageAccess;
import com.attestationhub.sailpoint.server.service.ManageAccessService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/manageaccess")
@Slf4j
@Tag(name = "Manage Access Service", description = "API to perform action on Manage Access")
public class ManageAccessController {
	
	@Resource
	private ManageAccessService manageAccessService;
	
	
	@GetMapping(value="/users/{owner}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsers(@PathVariable String owner, HttpServletRequest httpServletRequest) throws Exception{
		try {
			log.info("Request received to get Users {} ",owner);
			SailpointUserAccessResponse response = manageAccessService.getUsers(owner);
			if(ObjectUtils.isEmpty(response)) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception ap) {
			log.error("Failed to save the customer, failure reason {}",ap);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure"); 
		}
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserAccessForOwner(@RequestParam String owner, HttpServletRequest httpServletRequest) throws Exception{
		try {
			log.info("Request received to get Users {} ",owner);
			SailpointUserAccessResponse response = manageAccessService.getUserAccessForOwner(owner);
			if(ObjectUtils.isEmpty(response)) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception ap) {
			log.error("Failed to save the customer, failure reason {}",ap);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure"); 
		}
	}
	
	
	@PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserAccessForOwner(@RequestBody SailpointUserAccessResponse userAccess, HttpServletRequest httpServletRequest) throws Exception{
		try {
			log.info("Request received to get Users {} ",userAccess.getRequestor());
			SailpointUserAccessResponse response = manageAccessService.createUserAccess(userAccess);
			if(ObjectUtils.isEmpty(response)) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception ap) {
			log.error("Failed to save the customer, failure reason {}",ap);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure"); 
		}
	}
	
	
}
