package com.attestationhub.sailpoint.server.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController("/error")
@Slf4j
public class AttestationHubServerErrorController implements ErrorController{
	
	@GetMapping
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.error("Sailpoint Error Handler , Status {} ", status);

        //for brevity, only handling 404
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                log.error("404 Page not found.");
            }
        }
        return "forward:/#404";
    }

}
