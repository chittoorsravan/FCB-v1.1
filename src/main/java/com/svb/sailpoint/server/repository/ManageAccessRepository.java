package com.svb.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svb.sailpoint.server.entity.ManageAccess;

@Repository
public interface ManageAccessRepository extends CrudRepository<ManageAccess, Integer>{
	
	List<ManageAccess> findByrequester(String requester);

}
