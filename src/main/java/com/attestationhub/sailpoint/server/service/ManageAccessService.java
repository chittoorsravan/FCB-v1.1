package com.attestationhub.sailpoint.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attestationhub.sailpoint.server.dto.SailpointUserAccessResponse;
import com.attestationhub.sailpoint.server.dto.SailpointUsers;
import com.attestationhub.sailpoint.server.entity.ManageAccess;
import com.attestationhub.sailpoint.server.repository.ManageAccessRepository;
import com.attestationhub.sailpoint.server.repository.SailpointUserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManageAccessService {
	
	@Autowired
	private SailpointUserRepository sailpointUserRepository;
	
	@Autowired
	private ManageAccessRepository manageAccessRepository;

	public SailpointUserAccessResponse getUsers(String owner) {
		SailpointUserAccessResponse response = new SailpointUserAccessResponse();
		try {
			List<SailpointUsers> sailPointUsers = sailpointUserRepository.customSailPointUsers();
			response.setSailpointUsers(sailPointUsers);
			response.setStatus("success");
		}catch(Exception e) {
			response.setStatus("failure");
		}
		return response;
	}

	public SailpointUserAccessResponse getUserAccessForOwner(String owner) {
		SailpointUserAccessResponse response = new SailpointUserAccessResponse();
		try {
			List<ManageAccess> getAllAccess = manageAccessRepository.findByrequester(owner);
			response.setManageAccessList(getAllAccess);
			response.setStatus("success");
		}catch(Exception e) {
			response.setStatus("failure");
		}
		return response;
	}

	public SailpointUserAccessResponse createUserAccess(SailpointUserAccessResponse userAccess) {
		SailpointUserAccessResponse response = new SailpointUserAccessResponse();
		List<ManageAccess> manageAccess = userAccess.getManageAccessList();
		try {
			for(ManageAccess input:manageAccess) {
				ManageAccess ac = new ManageAccess();
				ac.setEntowner(input.getEntowner());
				ac.setEntname(input.getEntname());
				ac.setRequester(input.getRequester());
				ac.setRequestedFor(input.getRequestedFor());
				ac.setCreated(new Date());
				ac.setAction("Pending");
				ac.setDisplayname(input.getEntname());
				ac.setSignoffstatus("Pending");
				manageAccessRepository.save(ac);
			}
			response.setStatus("success");
		}catch(Exception e) {
			response.setStatus("failure");
		}
		return response;
	}
	
	
	
	
	
	
	
	

}
