package com.svb.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svb.sailpoint.server.entity.RAttestation;

@Repository
public interface RAttestationRepository extends CrudRepository<RAttestation, Integer>{

	List<RAttestation> findByAttestationnameAndOwner(String attestationname, String username);

	List<RAttestation> findByowner(String owner);
}
