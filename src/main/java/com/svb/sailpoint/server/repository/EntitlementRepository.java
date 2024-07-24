package com.svb.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svb.sailpoint.server.entity.Entitlement;

@Repository
public interface EntitlementRepository  extends CrudRepository<Entitlement, Integer>{

	List<Entitlement> findByAttestationnameAndEntowner(String attestationname, String username);

	List<Entitlement> findByentowner(String entowner);
}
