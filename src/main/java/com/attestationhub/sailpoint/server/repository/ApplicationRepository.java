package com.attestationhub.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.attestationhub.sailpoint.server.entity.Application;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Integer>{

	List<Application> findByAttestationnameAndOwner(String attestationname, String username);

	List<Application> findByowner(String owner);
	
	
	

}
