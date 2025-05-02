package com.akdoes.holiday.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the Nager.Date API is not available.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CountryNotAvailableException extends RuntimeException {
    
    public CountryNotAvailableException(String message) {
        super(message);
    }
    
    public CountryNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}