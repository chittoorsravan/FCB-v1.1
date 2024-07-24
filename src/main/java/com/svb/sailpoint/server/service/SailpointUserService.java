package com.svb.sailpoint.server.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.svb.sailpoint.server.entity.SailpointUser;
import com.svb.sailpoint.server.exception.NoDataFoundException;
import com.svb.sailpoint.server.exception.SailPointServerException;
import com.svb.sailpoint.server.repository.SailpointUserRepository;

@Service
public class SailpointUserService {
	
	@Autowired
	private SailpointUserRepository sailpointUserRepository;
	
	
	public void saveUser(String username,String password) throws SailPointServerException,Exception{
		SailpointUser user = sailpointUserRepository.findByUsername(username);
		if(user!=null) {
			throw new SailPointServerException(409,"User Already exist "+username);
		}
		SailpointUser sailpointUser = new SailpointUser();
		sailpointUser.setUsername(username);
		sailpointUser.setPassword(encodedPassword(password));
		sailpointUser.setCreatedOn(new Date());
		sailpointUserRepository.save(sailpointUser);
	}


	public SailpointUser findUser(String username) throws NoDataFoundException,Exception{
		SailpointUser user = sailpointUserRepository.findByUsername(username);
		if(user==null) {
			throw new NoDataFoundException();
		}
		return user;
	}
	
	
	private String encodedPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

}
