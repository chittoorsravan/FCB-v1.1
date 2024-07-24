package com.attestationhub.sailpoint.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.attestationhub.sailpoint.server.entity.UITableConfig;

@Repository
public interface UITableConfigurationRepository extends CrudRepository<UITableConfig, Integer>{

	List<UITableConfig> findBygridName(String attestationType);

}
