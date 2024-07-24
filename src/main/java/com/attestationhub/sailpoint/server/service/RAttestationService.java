package com.attestationhub.sailpoint.server.service;

import java.util.ArrayList;
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

import com.attestationhub.sailpoint.server.dto.ResponseAttributes;
import com.attestationhub.sailpoint.server.dto.SailpointRequest;
import com.attestationhub.sailpoint.server.dto.SailpointResponse;
import com.attestationhub.sailpoint.server.dto.WorkflowArgs;
import com.attestationhub.sailpoint.server.entity.RAttestation;
import com.attestationhub.sailpoint.server.entity.SailpointUser;
import com.attestationhub.sailpoint.server.exception.BadRequestException;
import com.attestationhub.sailpoint.server.exception.NoDataFoundException;
import com.attestationhub.sailpoint.server.exception.AttestationHubServerException;
import com.attestationhub.sailpoint.server.repository.RAttestationRepository;
import com.attestationhub.sailpoint.server.repository.SailpointUserRepository;
import com.attestationhub.sailpoint.server.utils.ApplicationTypes;
import com.attestationhub.sailpoint.server.utils.Attribute;
import com.attestationhub.sailpoint.server.utils.RequestorTypes;
import com.attestationhub.sailpoint.server.utils.ValueTypes;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RAttestationService {
	
	@Autowired
	private SailpointUserRepository sailpointUserRepository;
	
	@Autowired
	private RAttestationRepository rAttestationRepository;
	
	
	
public void saveAttestationDataFor(String username, String attestationName,String accessid,Integer rows, Attribute attribute, ApplicationTypes applicationTypes, RequestorTypes requestorTypes, ValueTypes valueTypes,Boolean commonaccess) throws AttestationHubServerException{
		
		SailpointUser user = sailpointUserRepository.findByUsername(username);
		if(user==null) {
			throw new NoDataFoundException(409,"User Doesn't exist "+username);
		}
		
		/*
		 * if(!CollectionUtils.isEmpty(applicationAttestations)) { throw new
		 * SailPointServerException(
		 * 409,"Application Attestation Data already Exist for user "+username); }
		 */
		
		insertApplicationRAttestationData(username, attestationName,accessid, rows, attribute, applicationTypes, requestorTypes, valueTypes,commonaccess);
	}
	
	
	
	private void insertApplicationRAttestationData(String owner,String attestationName,String accessid,Integer rows,Attribute attribute, ApplicationTypes applicationTypes, RequestorTypes requestorTypes,ValueTypes valueTypes,Boolean commonaccess) {
		log.info("Saving _R attestations");
		String attestationId = UUID.randomUUID().toString();
		for(int i=1;i<=rows;i++) {
			RAttestation application = new RAttestation();
			application.setOwner(owner);
			application.setRequester(requestorTypes.name());
			application.setAttestationid(attestationId);
			application.setApplication(applicationTypes.name());
			application.setAttestationname(attestationName);
			application.setAttribute(attribute.name());
			application.setCreated(new Date());
			application.setDescription("description "+application.getAttestationname());
			application.setAccessid(accessid+"abc"+i);
			application.setAccountname("210");
			application.setValue(valueTypes.name());
			application.setCommonaccess(commonaccess);
			rAttestationRepository.save(application);
		}
	}



	@SuppressWarnings("all")
	public SailpointResponse getRAttestations(SailpointRequest sailpointRequest) throws AttestationHubServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse sailpointResponse = new SailpointResponse();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		List<RAttestation> entitlementsByOwner =  rAttestationRepository.findByowner(workFlowArgs.getOwner());
		if(CollectionUtils.isEmpty(entitlementsByOwner)) {
			sailpointResponse.setSuccess(Boolean.TRUE);
			sailpointResponse.setRetry(Boolean.FALSE);
			sailpointResponse.setRetryWait(1);
			sailpointResponse.setStatus("No Data found for Owner"+workFlowArgs.getOwner());
			sailpointResponse.setAttributes(new ResponseAttributes(new HashMap<String, List<RAttestation>>()));
			return sailpointResponse;
		}
		//Map<String, List<RAttestation>> prodCommonIssueRecords = prodCommonFailureResponse(workFlowArgs.getOwner());
		Map<String, List<RAttestation>> dbRecords =  com.attestationhub.sailpoint.server.utils.CollectionUtils.groupBy(entitlementsByOwner, RAttestation :: getAttestationid);
		//dbRecords.put("8ae1833483e731c50183fc6d9a176d351708941624627", prodCommonIssueRecords.get("8ae1833483e731c50183fc6d9a176d351708941624627"));

		
		sailpointResponse.setAttributes(new ResponseAttributes<>(dbRecords));
		sailpointResponse.setSuccess(Boolean.TRUE);
		sailpointResponse.setRetry(Boolean.FALSE);
		sailpointResponse.setStatus("Success");
		sailpointResponse.setFailure(Boolean.FALSE);
		sailpointResponse.setRetryWait(0);
		sailpointResponse.setResponsetime(new Date().getTime());
		return sailpointResponse;
	}
	
	
	



	private HashMap<String, List<RAttestation>> prodCommonFailureResponse(String owner) {
		SailpointResponse sailpointResponse = new SailpointResponse();
		sailpointResponse.setRequestID("0a6102b38ddc1f06818de5e19dbd1d01");
		HashMap<String, List<RAttestation>> responseMap =   new HashMap<String, List<RAttestation>>();
		List<RAttestation> list = new ArrayList<>();
		
		RAttestation attestation1 = new RAttestation();
		
		attestation1.setOwner(owner);
		attestation1.setRequester("077514");
		attestation1.setAttestationid("8ae1833483e731c50183fc6d9a176d351708941624627");
		attestation1.setDescription("PKI Approvers Role for Operations.");
		attestation1.setAttestationname("_r Account Attestation for vbodnar_r(DMZ) 02/26/2024");
		attestation1.setAccessid("8ae1823a712d5d5c0171383ec76d49692");
		attestation1.setCommonaccess(Boolean.FALSE);
		attestation1.setApplication("DMZ Active Directory");
		attestation1.setAccountname("CN=Vladislav Bodnar (Admin Account),OU=Admin Accounts,OU=SVB,DC=dmz,DC=local");
		attestation1.setDisplayname("GS-PKI-Approvers");
		attestation1.setAttribute("memberOf");
		attestation1.setValue("CN=GS-PKI-Administrators,OU=Application,OU=Groups,OU=Administration,DC=dmz,DC=local");
		attestation1.setBusinessappname("Active Directory");
		
		list.add(attestation1);
		
		
		RAttestation attestation2 = new RAttestation();
		
		attestation2.setOwner(owner);
		attestation2.setRequester("077514");
		attestation2.setAttestationid("8ae1833483e731c50183fc6d9a176d351708941624627");
		attestation2.setDescription("WARNING PRIVILEGED ACCOUNT/ROLE\\\": Admin access;PKI Operator role for Ceremonies");
		attestation2.setAttestationname("_r Account Attestation for vbodnar_r(DMZ) 02/26/2024");
		attestation2.setAccessid("8ae1823a712d5d5c0171383ec76d49693");
		attestation2.setCommonaccess(Boolean.FALSE);
		attestation2.setApplication("DMZ Active Directory");
		attestation2.setAccountname("CN=Vladislav Bodnar (Admin Account),OU=Admin Accounts,OU=SVB,DC=dmz,DC=local");
		attestation2.setDisplayname("GS-PKI-Template-Administrators");
		attestation2.setAttribute("memberOf");
		attestation2.setValue("CN=GS-PKI-Administrators,OU=Application,OU=Groups,OU=Administration,DC=dmz,DC=local");
		attestation2.setBusinessappname("Active Directory");
        
		
		RAttestation attestation3 = new RAttestation();
		
		attestation3.setOwner(owner);
		attestation3.setRequester("077514");
		attestation3.setAttestationid("8ae1833483e731c50183fc6d9a176d351708941624627");
		attestation3.setDescription("WARNING PRIVILEGED ACCOUNT/ROLE\\\": Admin access;PKI Operator role for Ceremonies");
		attestation3.setAttestationname("_r Account Attestation for vbodnar_r(DMZ) 02/26/2024");
		attestation3.setAccessid("8ae1823a712d5d5c0171383ec76d49694");
		attestation3.setCommonaccess(Boolean.FALSE);
		attestation3.setApplication("DMZ Active Directory");
		attestation3.setAccountname("CN=Vladislav Bodnar (Admin Account),OU=Admin Accounts,OU=SVB,DC=dmz,DC=local");
		attestation3.setDisplayname("GS-PKI-Template-Administrators");
		attestation3.setAttribute("memberOf");
		attestation3.setValue("CN=GS-PKI-Administrators,OU=Application,OU=Groups,OU=Administration,DC=dmz,DC=local");
		attestation3.setBusinessappname("Active Directory");
		
		list.add(attestation3);
		
		RAttestation attestation4 = new RAttestation();
		
		attestation4.setOwner(owner);
		attestation4.setRequester("077514");
		attestation4.setAttestationid("8ae1833483e731c50183fc6d9a176d351708941624627");
		attestation4.setDescription("RAMESH PRIVILEGED ACCOUNT/ROLE\\\": Admin access;PKI Operator role for Ceremonies");
		attestation4.setAttestationname("_r Account Attestation for vbodnar_r(DMZ) 02/26/2024");
		attestation4.setAccessid("9ae1823a712d5d5c0171383ec78c496a");
		attestation4.setCommonaccess(Boolean.FALSE);
		attestation4.setApplication("DMZ Active Directory");
		attestation4.setAccountname("CN=Vladislav Bodnar (Admin Account),OU=Admin Accounts,OU=SVB,DC=dmz,DC=local");
		attestation4.setDisplayname("GS-PKI-Template-Administrators");
		attestation4.setAttribute("memberOf");
		attestation4.setValue("CN=GS-PKI-Administrators,OU=Application,OU=Groups,OU=Administration,DC=dmz,DC=local");
		attestation4.setBusinessappname("Active Directory");
		
		list.add(attestation4);
		
		responseMap.put("8ae1833483e731c50183fc6d9a176d351708941624627", list);
		
		return responseMap;
	}



	public SailpointResponse updateRAttestations(SailpointRequest<RAttestation> sailpointRequest) throws AttestationHubServerException,NoDataFoundException,BadRequestException{
		WorkflowArgs<RAttestation> workFlowArgs = sailpointRequest.getWorkflowArgs();
		if(workFlowArgs==null) throw new BadRequestException("Workflow Args are not passed.");
		SailpointResponse sailpointResponse = new SailpointResponse();
		sailpointResponse.setRequestID(UUID.randomUUID().toString());
		List<RAttestation> items = workFlowArgs.getItemsList();
		Map<Integer, RAttestation> map = items.stream().collect(Collectors.toMap(RAttestation::getId, Function.identity()));
		List<RAttestation> recordsByIds = (List<RAttestation>) rAttestationRepository.findAllById(items.stream().map(RAttestation::getId).collect(Collectors.toList()));
		
		for(RAttestation app : recordsByIds) {
			RAttestation input = map.get(app.getId());
			app.setCompletioncomments(input.getCompletioncomments());
			app.setSignoffstatus(input.getSignoffstatus());
			if(StringUtils.hasText(input.getSignoffstatus()) && input.getSignoffstatus().equalsIgnoreCase("Completed")){
				app.setSignoffdate(new Date());
			}
			app.setAction(input.getAction());
			rAttestationRepository.save(app);
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
