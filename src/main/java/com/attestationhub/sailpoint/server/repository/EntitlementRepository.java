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
	
    @Query(value = "SELECT  count(DISTINCT ATTESTATION_NAME) as count , COALESCE(a.signoffstatus, 'pending') AS action, a.ATTESTATION_NAME " +
            "FROM Entitlement a " +
            "WHERE a.owner = :owner " +
            "GROUP BY COALESCE(a.signoffstatus, 'pending'),a.ATTESTATION_NAME", nativeQuery = true)
	List<Object[]> countByActionAndOwner(@Param("owner") String owner);
    
}
