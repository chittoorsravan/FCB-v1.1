package com.svb.sailpoint.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.svb.sailpoint.server.dto.ResponseAttributes;
import com.svb.sailpoint.server.dto.SailpointRequest;
import com.svb.sailpoint.server.dto.SailpointResponse;
import com.svb.sailpoint.server.dto.WorkflowArgs;
import com.svb.sailpoint.server.entity.Entitlement;
import com.svb.sailpoint.server.entity.SailpointUser;
import com.svb.sailpoint.server.exception.BadRequestException;
import com.svb.sailpoint.server.exception.NoDataFoundException;
import com.svb.sailpoint.server.exception.SailPointServerException;
import com.svb.sailpoint.server.repository.EntitlementRepository;
import com.svb.sailpoint.server.repository.SailpointUserRepository;
import com.svb.sailpoint.server.utils.ApplicationTypes;
import com.svb.sailpoint.server.utils.Attribute;
import com.svb.sailpoint.server.utils.RequestorTypes;
import com.svb.sailpoint.server.utils.ValueTypes;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EntitlementAttestationService {
	

	@Autowired
	private SailpointUserRepository sailpointUserRepository;
	
	@Autowired
	private EntitlementRepository entitlementRepository;
	
	
	
	
	public void saveAttestationDataFor(String username, String attestationName, Integer rows, Attribute attribute,
			ApplicationTypes applicationTypes, RequestorTypes requestorTypes, ValueTypes valueTypes) throws SailPointServerException{
		
		SailpointUser user = sailpointUserRepository.findByUsername(username);
		if(user==null) {
			throw new NoDataFoundException(409,"User Doesn't exist "+username);
		}
		
		List<Entitlement> applicationAttestations = entitlementRepository.findByAttestationnameAndEntowner(attestationName,username);
		
		/*
		 * if(!CollectionUtils.isEmpty(applicationAttestations)) { throw new
		 * SailPointServerException(
		 * 409,"Application Attestation Data already Exist for user "+username); }
		 */
		
		insertApplicationAttestationData(username, attestationName, rows, attribute, applicationTypes, requestorTypes, valueTypes);
	}
	
	private void insertApplicationAttestationData(String owner,String attestationName,Integer rows,Attribute attribute, ApplicationTypes applicationTypes, RequestorTypes requestorTypes,ValueTypes valueTypes) {
		log.info("Saving entilement attestations");
		for(int i=1;i<=rows;i++) {
			Entitlement application = new Entitlement();
			application.setApplication(applicationTypes.name());
			application.setAttestationname(attestationName);
			application.setAttribute(attribute.name());
			application.setCreated(new Date());
			application.setEntowner(owner);
			application.setRequester(requestorTypes.name());
			application.setValue(valueTypes.name());
			application.setType("Entitlement");
			application.setIsprivileged(Boolean.FALSE);
			application.setIscertifiable(Boolean.FALSE);
			application.set_rrequired(0);
			application.setRequestable(1);
			application.setIsFavorite(Boolean.FALSE);
			application.setDescription(attribute.name()+":"+valueTypes.name());
			entitlementRepository.save(application);
		}
		
	}

	@SuppressWarnings("all")
	public SailpointResponse getEntitlementAttestations(SailpointRequest sailpointRequest) throws SailPointServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse sailpointResponse = new SailpointResponse();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		List<Entitlement> entitlementsByOwner =  entitlementRepository.findByentowner(workFlowArgs.getOwner());
		if(CollectionUtils.isEmpty(entitlementsByOwner)) {
			sailpointResponse.setSuccess(Boolean.TRUE);
			sailpointResponse.setRetry(Boolean.FALSE);
			sailpointResponse.setRetryWait(1);
			sailpointResponse.setStatus("No Data found for Owner"+workFlowArgs.getOwner());
			sailpointResponse.setAttributes(new ResponseAttributes(new HashMap<String, List<Entitlement>>()));
			return sailpointResponse;
		}
		sailpointResponse.setAttributes(new ResponseAttributes<>( com.svb.sailpoint.server.utils.CollectionUtils.groupBy(entitlementsByOwner, Entitlement :: getAttestationname)));
		sailpointResponse.setSuccess(Boolean.TRUE);
		sailpointResponse.setRetry(Boolean.FALSE);
		sailpointResponse.setStatus("Success");
		sailpointResponse.setFailure(Boolean.FALSE);
		sailpointResponse.setRetryWait(0);
		sailpointResponse.setResponsetime(new Date().getTime());
		return sailpointResponse;
	}
	
	
	public SailpointResponse updateEntitlementAttestations(SailpointRequest<Entitlement> sailpointRequest) throws SailPointServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs<Entitlement> workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse sailpointResponse = new SailpointResponse();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		
		List<Entitlement> items = workFlowArgs.getItemsList();
		Map<Integer, Entitlement> map = items.stream().collect(Collectors.toMap(Entitlement::getId, Function.identity()));
		List<Entitlement> recordsByIds = (List<Entitlement>) entitlementRepository.findAllById(items.stream().map(Entitlement::getId).collect(Collectors.toList()));
		for(Entitlement app : recordsByIds) {
			Entitlement input = map.get(app.getId());
			app.setDisplay_name(input.getDisplay_name());
			app.setDescription(input.getDescription());
			app.setIsprivileged(input.getIsprivileged());
			app.setIscertifiable(input.getIscertifiable());
			app.setRequestable(input.getRequestable());
			app.setCompletioncomments(input.getCompletioncomments());
			app.setSignoffstatus(input.getSignoffstatus());
			app.set_rrequired(input.get_rrequired());
			if(StringUtils.hasText(input.getSignoffstatus()) && input.getSignoffstatus().equalsIgnoreCase("Completed")){
				app.setSignoffdate(new Date());
			}
			app.setAction(input.getAction());
			entitlementRepository.save(app);
		}
		
		sailpointResponse.setSuccess(Boolean.FALSE);
		sailpointResponse.setRetry(Boolean.FALSE);
		sailpointResponse.setFailure(Boolean.FALSE);
		sailpointResponse.setRetryWait(0);
		sailpointResponse.setComplete(Boolean.FALSE);
		sailpointResponse.setResponsetime(new Date().getTime());
		return sailpointResponse;
	}

	public SailpointResponse setFavorites(Entitlement entitlement,String owner) throws Exception{
		SailpointResponse sailpointResponse = new SailpointResponse();
		List<Entitlement> recordsByIds = (List<Entitlement>) entitlementRepository.findByAttestationnameAndEntowner(entitlement.getAttestationname(),owner);
		for(Entitlement app : recordsByIds) {
			app.setIsFavorite(entitlement.getIsFavorite());
			entitlementRepository.save(app);
		}
		sailpointResponse.setSuccess(Boolean.TRUE);
		sailpointResponse.setRetry(Boolean.FALSE);
		sailpointResponse.setFailure(Boolean.FALSE);
		sailpointResponse.setRetryWait(0);
		sailpointResponse.setComplete(Boolean.FALSE);
		sailpointResponse.setResponsetime(new Date().getTime());
		return sailpointResponse;
	}
	

}
