package com.attestationhub.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.attestationhub.sailpoint.server.dto.SailpointUsers;
import com.attestationhub.sailpoint.server.entity.SailpointUser;

@Repository
public interface SailpointUserRepository extends CrudRepository<SailpointUser, Long>{
	
	SailpointUser findByUsername(String username);
	
	@Query("SELECT new com.attestationhub.sailpoint.server.dto.SailpointUsers(c.username) "
			  + "FROM SailpointUser AS c ORDER BY c.username ASC")
	List<SailpointUsers> customSailPointUsers();
	
}
