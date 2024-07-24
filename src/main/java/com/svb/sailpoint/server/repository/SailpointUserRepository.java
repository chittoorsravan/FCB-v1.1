package com.svb.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svb.sailpoint.server.dto.SailpointUsers;
import com.svb.sailpoint.server.entity.SailpointUser;

@Repository
public interface SailpointUserRepository extends CrudRepository<SailpointUser, Long>{
	
	SailpointUser findByUsername(String username);
	
	@Query("SELECT new com.svb.sailpoint.server.dto.SailpointUsers(c.username) "
			  + "FROM SailpointUser AS c ORDER BY c.username ASC")
	List<SailpointUsers> customSailPointUsers();
	
}
