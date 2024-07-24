package com.attestationhub.sailpoint.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attestationhub.sailpoint.server.dto.UITableConfigDto;
import com.attestationhub.sailpoint.server.entity.UITableConfig;
import com.attestationhub.sailpoint.server.repository.UITableConfigurationRepository;

@Service
public class UITableConfigurationService {
	
	@Autowired
	private UITableConfigurationRepository uiTableConfigurationRepository;

	public void createTableColConfig(UITableConfigDto uiTableConfigDto) {
		UITableConfig uitableConfig = new UITableConfig();
		BeanUtils.copyProperties(uiTableConfigDto, uitableConfig);	
		uitableConfig.setCreatedBy("System");
		uitableConfig.setCreatedOn(new Date());
		uiTableConfigurationRepository.save(uitableConfig);		
	}

	public List<UITableConfig> getTableGridConfig(String attestationType) {
		return uiTableConfigurationRepository.findBygridName(attestationType);
	}

	public void updateTableGridConfig(UITableConfigDto uiTableConfigDto) {
		// TODO Auto-generated method stub
		
	}

}
