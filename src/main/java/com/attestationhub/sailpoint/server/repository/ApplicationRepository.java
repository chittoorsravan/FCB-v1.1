package com.attestationhub.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.attestationhub.sailpoint.server.entity.Application;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Integer> {

	List<Application> findByAttestationnameAndOwner(String attestationname, String username);

	List<Application> findByowner(String owner);
	
	@Query(value = "SELECT COALESCE(a.action, 'pending') AS action, COUNT(*) AS count,a.attestation_name " +
            "FROM Application a " +
            "WHERE a.owner = :owner " +
            "GROUP BY COALESCE(a.action, 'pending'),a.attestation_name", nativeQuery = true)
	List<Object[]> countByActionAndOwner(@Param("owner") String owner);

}
