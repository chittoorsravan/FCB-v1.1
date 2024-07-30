package com.attestationhub.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.attestationhub.sailpoint.server.entity.Entitlement;

@Repository
public interface EntitlementRepository  extends CrudRepository<Entitlement, Integer>{

	List<Entitlement> findByAttestationnameAndOwner(String attestationname, String username);

	List<Entitlement> findByOwner(String owner);
	
    @Query(value = "SELECT  count(DISTINCT attestation_name) as count , COALESCE(a.sign_off_status, 'pending') AS action, a.attestation_name " +
            "FROM Entitlement a " +
            "WHERE a.owner = :owner " +
            "GROUP BY a.action,a.attestation_name", nativeQuery = true)
	List<Object[]> countByActionAndOwner(@Param("owner") String owner);
    
}
