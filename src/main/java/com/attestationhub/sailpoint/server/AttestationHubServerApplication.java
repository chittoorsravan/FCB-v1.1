package com.attestationhub.sailpoint.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AttestationHubServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttestationHubServerApplication.class, args);
	}

}
