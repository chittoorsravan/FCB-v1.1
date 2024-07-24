package com.svb.sailpoint.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.svb.sailpoint.server.dto.UITableConfigDto;
import com.svb.sailpoint.server.entity.UITableConfig;
import com.svb.sailpoint.server.service.UITableConfigurationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/uitable")
@Slf4j
@Tag(name = "UI Table Configuration Service", description = "API to perform action on UI Table Configuration")
public class UITableConfigurationController {
	
	@Autowired
	private UITableConfigurationService uiTableConfigurationService;	
	
	@PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody UITableConfigDto uiTableConfigDto,
					HttpServletRequest httpServletRequest) throws Exception{
			log.info("Request received to create a UI Table Configuration with name {} ",uiTableConfigDto.getColName());
			uiTableConfigurationService.createTableColConfig(uiTableConfigDto);		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	@GetMapping(value="/get",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getApplicationsForUser(HttpServletRequest httpServletRequest,@RequestParam String attestationType) throws Exception{
			log.info("Request received to get attestationType for  {} ",attestationType);
			List<UITableConfig> response = uiTableConfigurationService.getTableGridConfig(attestationType);
			if(response==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PutMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateApplicationForUser(HttpServletRequest httpServletRequest, @RequestBody UITableConfigDto uiTableConfigDto) throws Exception{
			log.info("Update Request received to update table grid config{} ",uiTableConfigDto.getColName());
			 uiTableConfigurationService.updateTableGridConfig(uiTableConfigDto);
			return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
	}

}
