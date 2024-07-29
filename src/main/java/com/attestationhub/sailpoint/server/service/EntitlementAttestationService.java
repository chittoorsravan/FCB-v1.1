package com.attestationhub.sailpoint.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.attestationhub.sailpoint.server.dto.ResponseAttributes;
import com.attestationhub.sailpoint.server.dto.SailpointRequest;
import com.attestationhub.sailpoint.server.dto.SailpointResponse;
import com.attestationhub.sailpoint.server.dto.SummaryResponseAttributes;
import com.attestationhub.sailpoint.server.dto.SummaryResponseMap;
import com.attestationhub.sailpoint.server.dto.WorkflowArgs;
import com.attestationhub.sailpoint.server.entity.Entitlement;
import com.attestationhub.sailpoint.server.entity.SailpointUser;
import com.attestationhub.sailpoint.server.exception.AttestationHubServerException;
import com.attestationhub.sailpoint.server.exception.BadRequestException;
import com.attestationhub.sailpoint.server.exception.NoDataFoundException;
import com.attestationhub.sailpoint.server.repository.EntitlementRepository;
import com.attestationhub.sailpoint.server.repository.SailpointUserRepository;
import com.attestationhub.sailpoint.server.repository.UniqueIdRepository;
import com.attestationhub.sailpoint.server.utils.ApplicationTypes;
import com.attestationhub.sailpoint.server.utils.Attribute;
import com.attestationhub.sailpoint.server.utils.RequestorTypes;
import com.attestationhub.sailpoint.server.utils.ValueTypes;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EntitlementAttestationService {
	

	@Autowired
	private SailpointUserRepository sailpointUserRepository;
	
	@Autowired
	private EntitlementRepository entitlementRepository;
	
	@Autowired
	private UniqueIdRepository uniqueIdRepository;
	
	
	
	
	public void saveAttestationDataFor(String username, String attestationName, Integer rows, Attribute attribute,
			ApplicationTypes applicationTypes, RequestorTypes requestorTypes, ValueTypes valueTypes) throws AttestationHubServerException{
		
		SailpointUser user = sailpointUserRepository.findByUsername(username);
		if(user==null) {
			throw new NoDataFoundException(409,"User Doesn't exist "+username);
		}
		insertEntitlementAttestationData(username, attestationName, rows, attribute, applicationTypes, requestorTypes, valueTypes);
	}
	
	private void insertEntitlementAttestationData(String owner,String attestationName,Integer rows,Attribute attribute, ApplicationTypes applicationTypes, RequestorTypes requestorTypes,ValueTypes valueTypes) {
		log.info("Saving entilement attestations");
		String attestationId = UUID.randomUUID().toString();
		Long workItem = uniqueIdRepository.getNextSequenceValue();
		for(int i=1;i<=rows;i++) {
			Entitlement application = new Entitlement();
			application.setApplication(applicationTypes.name());
			application.setAttestationname(attestationName);
			application.setAttribute(attribute.name());
			application.setCreated(new Date());
			application.setOwner(owner);
			application.setRequester(requestorTypes.name());
			application.setValue(valueTypes.name());
			application.setType("Entitlement");
			application.setIsprivileged(Boolean.FALSE);
			application.setIscertifiable(Boolean.FALSE);
			application.setBusinessappname(applicationTypes.name());
			application.setIsrequestable(Boolean.FALSE);
			application.setIssensitive(Boolean.FALSE);
			application.setAdmAccessRequired(Boolean.FALSE);
			application.setDuedate(DateUtils.addDays(application.getCreated(), 30));
			application.setDisplayname(application.getValue());
			application.setAppid("APM00"+workItem);
			application.setAction("pending");
			application.setAccessid(attestationId+"abc"+i);
			application.setWorkitem(""+workItem);
			application.setDescription(application.getValue()+" Access for "+application.getApplication());
			entitlementRepository.save(application);
		}
		
	}

	@SuppressWarnings("all")
	public SailpointResponse<ResponseAttributes<Entitlement>> getEntitlementAttestations(SailpointRequest sailpointRequest) throws AttestationHubServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse sailpointResponse = new SailpointResponse();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		List<Entitlement> entitlementsByOwner =  entitlementRepository.findByOwner(workFlowArgs.getOwner());
		if(CollectionUtils.isEmpty(entitlementsByOwner)) {
			sailpointResponse.setSuccess(Boolean.TRUE);
			sailpointResponse.setRetry(Boolean.FALSE);
			sailpointResponse.setRetryWait(1);
			sailpointResponse.setStatus("No Data found for Owner"+workFlowArgs.getOwner());
			sailpointResponse.setAttributes(new ResponseAttributes(new HashMap<String, List<Entitlement>>()));
			return sailpointResponse;
		}
		sailpointResponse.setAttributes(new ResponseAttributes<>( com.attestationhub.sailpoint.server.utils.CollectionUtils.groupBy(entitlementsByOwner, Entitlement :: getAttestationname)));
		sailpointResponse.setSuccess(Boolean.TRUE);
		sailpointResponse.setRetry(Boolean.FALSE);
		sailpointResponse.setStatus("Success");
		sailpointResponse.setFailure(Boolean.FALSE);
		sailpointResponse.setRetryWait(0);
		sailpointResponse.setResponsetime(new Date().getTime());
		return sailpointResponse;
	}
	
	
	@SuppressWarnings("all")
	public SailpointResponse<SummaryResponseAttributes> getEntitlementAttestationsSummary(SailpointRequest sailpointRequest,Integer limit) throws AttestationHubServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse<SummaryResponseAttributes> sailpointResponse = new SailpointResponse<SummaryResponseAttributes>();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		
		SummaryResponseMap summaryMap =  getActionCountsByOwner(workFlowArgs.getOwner());
		if(summaryMap == null || CollectionUtils.isEmpty(summaryMap.getTop5items()) ) {
			sailpointResponse.setSuccess(Boolean.TRUE);
			sailpointResponse.setRetry(Boolean.FALSE);
			sailpointResponse.setRetryWait(1);
			sailpointResponse.setStatus("No Data found for Owner"+workFlowArgs.getOwner());
			sailpointResponse.setAttributes(new SummaryResponseAttributes(new SummaryResponseMap()));
			return sailpointResponse;
		}
		
		sailpointResponse.setAttributes(new SummaryResponseAttributes(summaryMap));
		
		sailpointResponse.setSuccess(Boolean.TRUE);
		sailpointResponse.setRetry(Boolean.FALSE);
		sailpointResponse.setStatus("Success");
		sailpointResponse.setFailure(Boolean.FALSE);
		sailpointResponse.setRetryWait(0);
		sailpointResponse.setResponsetime(new Date().getTime());
		return sailpointResponse;
	}
	
	public SummaryResponseMap getActionCountsByOwner(String owner) {
	    List<Object[]> results = entitlementRepository.countByActionAndOwner(owner);
	    SummaryResponseMap summaryMap = new SummaryResponseMap();
	    List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	    Map<String, String> attestationNamesWithAction = new LinkedHashMap<String, String>();
	    
	    for (Object[] row : results) {
	    	String action = (String) row[0];
	    	String attestationname = (String) row[2];
	        if(StringUtils.hasText(action) ||  "pending".equalsIgnoreCase(action) ) summaryMap.setPending(summaryMap.getPending() +  ((Number) row[1]).intValue());
	        if(StringUtils.hasText(action) ||  "pending".equalsIgnoreCase(action) ) summaryMap.setPending(summaryMap.getCompleted() +  ((Number) row[1]).intValue());
	        if(StringUtils.hasText(action) ||  "closed".equalsIgnoreCase(action) ) summaryMap.setPending(summaryMap.getAutoClosed() +  ((Number) row[1]).intValue());
	        if(StringUtils.hasText(attestationname) && !attestationNamesWithAction.containsKey(attestationname)) {
	        	attestationNamesWithAction.put(attestationname, action);
	        }
	    }
	    list.add(attestationNamesWithAction);
	    summaryMap.setTop5items(list);
	    return summaryMap;
	}
	
	public SailpointResponse<ResponseAttributes<Entitlement>> updateEntitlementAttestations(SailpointRequest<Entitlement> sailpointRequest) throws AttestationHubServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs<Entitlement> workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse<ResponseAttributes<Entitlement>> sailpointResponse = new SailpointResponse<ResponseAttributes<Entitlement>>();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		
		List<Entitlement> items = workFlowArgs.getItemsList();
		Map<Integer, Entitlement> map = items.stream().collect(Collectors.toMap(Entitlement::getId, Function.identity()));
		List<Entitlement> recordsByIds = (List<Entitlement>) entitlementRepository.findAllById(items.stream().map(Entitlement::getId).collect(Collectors.toList()));
		for(Entitlement app : recordsByIds) {
			Entitlement input = map.get(app.getId());
			
			app.setDisplayname(input.getDisplayname());
			app.setDescription(input.getDescription());
			app.setIsprivileged(input.getIsprivileged());
			app.setIscertifiable(input.getIscertifiable());
			app.setIsrequestable(input.getIsrequestable());
			app.setCompletioncomments(input.getCompletioncomments());
			app.setSignoffstatus(input.getSignoffstatus());
			app.setIssensitive(input.getIssensitive());
			app.setAdmAccessRequired(input.getAdmAccessRequired());

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

	public SailpointResponse<ResponseAttributes<Entitlement>> setFavorites(Entitlement entitlement,String owner) throws Exception{
		SailpointResponse<ResponseAttributes<Entitlement>> sailpointResponse = new SailpointResponse<ResponseAttributes<Entitlement>>();
		List<Entitlement> recordsByIds = (List<Entitlement>) entitlementRepository.findByAttestationnameAndOwner(entitlement.getAttestationname(),owner);
		for(Entitlement app : recordsByIds) {
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
