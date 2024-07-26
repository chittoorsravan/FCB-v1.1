package com.attestationhub.sailpoint.server.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.attestationhub.sailpoint.server.entity.Entitlement;

@Repository
public interface EntitlementRepository  extends CrudRepository<Entitlement, Integer>{

	List<Entitlement> findByAttestationnameAndOwner(String attestationname, String username);

	List<Entitlement> findByOwner(String owner);
	
    @Query(value = "SELECT a.action AS action, COUNT(*) AS count " +
            "FROM Entitlement a " +
            "WHERE a.owner = :owner " +
            "GROUP BY a.action", nativeQuery = true)
	List<Object[]> countByActionAndOwner(@Param("owner") String owner);
    
    @Query("SELECT a.attestationname FROM Entitlement a " + "WHERE a.owner = :owner "
			+ "AND (a.action IS NULL OR a.action IN ('pending', 'completed', 'closed')) GROUP BY a.attestationname " + "ORDER BY CASE "
			+ "           WHEN a.action IS NULL OR a.action = 'pending' THEN 1 "
			+ "			 WHEN a.action = 'completed' THEN 2 "
			+ "           WHEN a.action = 'closed' THEN 3 " + " "
			+ "          ELSE 4 " + " END, MIN(a.created) ASC")
	List<String> findTopAttestationNamesByOwner(@Param("owner") String owner, Pageable pageable);
	
	
}
