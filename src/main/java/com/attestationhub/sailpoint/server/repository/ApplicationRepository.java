package com.attestationhub.sailpoint.server.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.attestationhub.sailpoint.server.entity.Application;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Integer> {

	List<Application> findByAttestationnameAndOwner(String attestationname, String username);

	List<Application> findByowner(String owner);
	
	@Query(value = "SELECT a.action AS action, COUNT(*) AS count " +
            "FROM Application a " +
            "WHERE a.owner = :owner " +
            "GROUP BY a.action", nativeQuery = true)
	List<Object[]> countByActionAndOwner(@Param("owner") String owner);

	@Query("SELECT a.attestationname FROM Application a " + "WHERE a.owner = :owner "
			+ "AND a.action IN ('pending', 'completed', 'closed') GROUP BY a.attestationname " + "ORDER BY CASE "
			+ "           WHEN a.action = 'pending' THEN 1 "
			+ "			 WHEN a.action = 'completed' THEN 2 "
			+ "           WHEN a.action = 'closed' THEN 3 " + " "
			+ "          ELSE 4 " + " END, MIN(a.created) ASC")
	List<String> findTopAttestationNamesByOwner(@Param("owner") String owner, Pageable pageable);

}
