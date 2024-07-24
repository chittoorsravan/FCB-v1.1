package com.svb.sailpoint.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.svb.sailpoint.server.entity.SailpointUser;
import com.svb.sailpoint.server.service.SailpointUserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "Sailpoint User Service", description = "API to perform actions on User")
public class UserController {
	
	@Autowired
	private SailpointUserService sailpointUserService;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestParam String username, @RequestParam String password, HttpServletRequest httpServletRequest) throws Exception{
		try {
			log.info("Request received to create a customer with name {} ",username);
			sailpointUserService.saveUser(username,password);		
		}catch(Exception ap) {
			log.error("Failed to save the customer, failure reason {}",ap);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure"); 
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findUser(@RequestParam String username,  HttpServletRequest httpServletRequest) throws Exception{
		try {
			log.info("Request received to create a customer with name {} ",username);
			SailpointUser user = sailpointUserService.findUser(username);		
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}catch(Exception ap) {
			log.error("Failed to save the customer, failure reason {}",ap);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure"); 
		}
	}

}
