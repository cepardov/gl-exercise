package com.globallogic.exercise.advice;

import com.globallogic.exercise.exception.DateSelectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EarthquakeControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(EarthquakeControllerAdvice.class);

    @ExceptionHandler(DateSelectedException.class)
    public ResponseEntity<Object> entityViolations(DateSelectedException e) {
        logger.error(e.getMessage(), e);
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
