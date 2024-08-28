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
import com.attestationhub.sailpoint.server.entity.Application;
import com.attestationhub.sailpoint.server.entity.SailpointUser;
import com.attestationhub.sailpoint.server.exception.AttestationHubServerException;
import com.attestationhub.sailpoint.server.exception.BadRequestException;
import com.attestationhub.sailpoint.server.exception.NoDataFoundException;
import com.attestationhub.sailpoint.server.repository.ApplicationRepository;
import com.attestationhub.sailpoint.server.repository.SailpointUserRepository;
import com.attestationhub.sailpoint.server.repository.UniqueIdRepository;
import com.attestationhub.sailpoint.server.utils.ApplicationTypes;
import com.attestationhub.sailpoint.server.utils.Attribute;
import com.attestationhub.sailpoint.server.utils.RequestorTypes;
import com.attestationhub.sailpoint.server.utils.ValueTypes;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ApplicationAttestationService {
	
	
	@Autowired
	private SailpointUserRepository sailpointUserRepository;
	
	@Autowired
	private UniqueIdRepository uniqueIdRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	public void saveAttestationDataFor(String username, String attestationName,Integer rows, Attribute attribute, ApplicationTypes applicationTypes, RequestorTypes requestorTypes, ValueTypes valueTypes) throws AttestationHubServerException{
		
		SailpointUser user = sailpointUserRepository.findByUsername(username);
		if(user==null) {
			throw new NoDataFoundException(409,"User Doesn't exist "+username);
		}
		
		insertApplicationAttestationData(username, attestationName, rows, attribute, applicationTypes, requestorTypes, valueTypes);
	}
	
	
	
	private void insertApplicationAttestationData(String owner,String attestationName,Integer rows,Attribute attribute, ApplicationTypes applicationTypes, RequestorTypes requestorTypes,ValueTypes valueTypes) {
		log.info("Saving Application attestations");
		String attestationId = UUID.randomUUID().toString();
		Long workItem = uniqueIdRepository.getNextSequenceValue();
		for(int i=1;i<=rows;i++) {
			Application application = new Application();
			application.setApplication(applicationTypes.name());
			application.setAttestationname(attestationName);
			application.setAttribute(attribute.name());
			application.setCreated(new Date());
			application.setOwner(owner);
			application.setRequester(requestorTypes.name());
			application.setValue(valueTypes.name());
			application.setAccessid(attestationId+"abc"+i);
			application.setType("Application");
			application.setDuedate(DateUtils.addDays(application.getCreated(), 30));
			application.setDisplayname(application.getValue());
			application.setWorkitem(""+workItem);
			application.setAction("pending");
			if(valueTypes.name().equalsIgnoreCase(ValueTypes.AUTOCLOSED.name())) {
				application.setAction("Autoclosed");
				application.setSignoffstatus("Autoclosed");
				application.setSignoffdate(new Date());
			}
			application.setDescription(application.getValue()+" Access for "+application.getApplication());
			applicationRepository.save(application);
		}
		
	}



	@SuppressWarnings("all")
	public SailpointResponse<ResponseAttributes<Application>> getApplicationAttestations(SailpointRequest sailpointRequest) throws AttestationHubServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse<ResponseAttributes<Application>> sailpointResponse = new SailpointResponse<ResponseAttributes<Application>>();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		List<Application> entitlementsByOwner =  applicationRepository.findByowner(workFlowArgs.getOwner());
		if(CollectionUtils.isEmpty(entitlementsByOwner)) {
			sailpointResponse.setSuccess(Boolean.TRUE);
			sailpointResponse.setRetry(Boolean.FALSE);
			sailpointResponse.setRetryWait(1);
			sailpointResponse.setStatus("No Data found for Owner"+workFlowArgs.getOwner());
			sailpointResponse.setAttributes(new ResponseAttributes(new HashMap<String, List<Application>>()));
			return sailpointResponse;
		}
		sailpointResponse.setAttributes(new ResponseAttributes<>( com.attestationhub.sailpoint.server.utils.CollectionUtils.groupBy(entitlementsByOwner, Application :: getAttestationname)));
		sailpointResponse.setSuccess(Boolean.TRUE);
		sailpointResponse.setRetry(Boolean.FALSE);
		sailpointResponse.setStatus("Success");
		sailpointResponse.setFailure(Boolean.FALSE);
		sailpointResponse.setRetryWait(0);
		sailpointResponse.setResponsetime(new Date().getTime());
		return sailpointResponse;
	}
	
	
	@SuppressWarnings("all")
	public SailpointResponse<SummaryResponseAttributes> getApplicationAttestationsSummary(SailpointRequest sailpointRequest,Integer limit) throws AttestationHubServerException,NoDataFoundException,BadRequestException{
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
	    List<Object[]> results = applicationRepository.countByActionAndOwner(owner);
	    SummaryResponseMap summaryMap = new SummaryResponseMap();
	    List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	    Map<String, String> attestationNamesWithAction = new LinkedHashMap<String, String>();
	    
	    for (Object[] row : results) {
	    	String action = (String) row[1];
	    	String attestationname = (String) row[2];
	        if(StringUtils.hasText(action) &&  "pending".equalsIgnoreCase(action) ) summaryMap.setPending(summaryMap.getPending() +  ((Number) row[0]).intValue());
	        if(StringUtils.hasText(action) &&  "completed".equalsIgnoreCase(action) ) summaryMap.setCompleted(summaryMap.getCompleted() +  ((Number) row[0]).intValue());
	        if(StringUtils.hasText(action) &&  "autoclosed".equalsIgnoreCase(action) ) summaryMap.setAutoclosed(summaryMap.getAutoclosed() +  ((Number) row[0]).intValue());
	        if(StringUtils.hasText(attestationname) && !attestationNamesWithAction.containsKey(attestationname)) {
	        	attestationNamesWithAction.put(attestationname, action);
	        }
	    }
	    
	    list.add(attestationNamesWithAction);
	    summaryMap.setTop5items(list);
	    return summaryMap;
	}



	public SailpointResponse<ResponseAttributes<Application>> updateApplicationAttestations(SailpointRequest<Application> sailpointRequest) throws AttestationHubServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs<Application> workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse<ResponseAttributes<Application>> sailpointResponse = new SailpointResponse<ResponseAttributes<Application>>();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		List<Application> items = workFlowArgs.getItemsList();
		Map<Integer, Application> map = items.stream().collect(Collectors.toMap(Application::getId, Function.identity()));
		List<Application> recordsByIds = (List<Application>) applicationRepository.findAllById(items.stream().map(Application::getId).collect(Collectors.toList()));
		
		for(Application app : recordsByIds) {
			Application input = map.get(app.getId());
			app.setCompletioncomments(input.getCompletioncomments());
			app.setSignoffstatus(input.getSignoffstatus());
			
			
			if(StringUtils.hasText(input.getSignoffstatus()) && input.getSignoffstatus().equalsIgnoreCase("Completed")){
				app.setSignoffdate(new Date());
			}
			app.setAction(input.getAction());
			if(StringUtils.hasText(workFlowArgs.getComments())){
				app.setComments(workFlowArgs.getComments());
			}
			applicationRepository.save(app);
		}
		
		sailpointResponse.setSuccess(Boolean.FALSE);
		sailpointResponse.setRetry(Boolean.FALSE);
		sailpointResponse.setFailure(Boolean.FALSE);
		sailpointResponse.setRetryWait(0);
		sailpointResponse.setComplete(Boolean.FALSE);
		sailpointResponse.setResponsetime(new Date().getTime());
		return sailpointResponse;
	}
	

}
