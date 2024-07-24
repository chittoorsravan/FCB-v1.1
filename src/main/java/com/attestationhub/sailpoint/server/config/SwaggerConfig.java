/**
 * 
 */
package com.attestationhub.sailpoint.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;



/**
 * @author rmunugal
 *
 */

@Configuration
@Profile({"swagger","local"})
@OpenAPIDefinition(info = @Info(
		        title = "AttestationHub Server API", 
				version = "1.0.0", 
				description = "AttestationHub Server"))
public class SwaggerConfig {

}
