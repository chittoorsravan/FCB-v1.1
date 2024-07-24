package com.svb.sailpoint.server.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.svb.sailpoint.server.exception.NoDataFoundException;
import com.svb.sailpoint.server.exception.SailPointServerException;

@ControllerAdvice
public class SaipointServerControllerAdvice extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(SailPointServerException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
        SailPointServerException ex, WebRequest request) {
        return new ResponseEntity<>(responseMessage(ex.getErrorCode(),ex.getErrorMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(
        NoDataFoundException ex, WebRequest request) {
        return new ResponseEntity<>(responseMessage(ex.getErrorCode(),ex.getErrorMessage()), HttpStatus.NO_CONTENT);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(responseMessage(500,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    
    
    private Map<String, Object> responseMessage(int errorCode,String errorMessage){
    	 Map<String, Object> body = new LinkedHashMap<>();
         body.put("timestamp", LocalDateTime.now());
         body.put("errorcode", errorCode);
         body.put("message", errorMessage);
         return body;
    }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, 
        HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
