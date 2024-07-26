package com.attestationhub.sailpoint.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.attestationhub.sailpoint.server.entity.UniqueIdEntity;

@Repository
public interface UniqueIdRepository extends CrudRepository<UniqueIdEntity, Long>{
	
	@Query(value = "SELECT NEXT VALUE FOR unique_sequence", nativeQuery = true)
	Long getNextSequenceValue();

}
