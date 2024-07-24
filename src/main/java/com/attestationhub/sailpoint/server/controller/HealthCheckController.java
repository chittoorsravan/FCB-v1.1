package com.attestationhub.sailpoint.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/health")
@Tag(name = "Health" , description = "SaiPoint Server Service API Health Check")
public class HealthCheckController {
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "SailPoint Server Health Check" , description = "Validates Sail Point Server Health")
	ResponseEntity<String> healthCheck() {
	    return new ResponseEntity<>("SailPoint Server is Up", HttpStatus.OK);
	}
	

}
