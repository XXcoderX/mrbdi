package com.mrbdi.vehiclerunmiles.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> returnException(Exception ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Service is unavailable");
		StringWriter s = new StringWriter();
		ex.printStackTrace(new PrintWriter(s));
		log.error(s.toString());
		return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
