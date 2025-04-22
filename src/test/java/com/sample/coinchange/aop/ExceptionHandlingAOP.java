package com.sample.coinchange.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlingAOPTest {

    private ExceptionHandlingAOP exceptionHandlingAOP;

    @BeforeEach
    void setUp() {
        exceptionHandlingAOP = new ExceptionHandlingAOP();
    }

    @Test
    void handleControllerExceptionsTest_IllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("test illegal argument exception");

        ResponseEntity<Object> response = exceptionHandlingAOP.handleControllerExceptions(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("test illegal argument exception", response.getBody());
    }

    @Test
    void handleControllerExceptionsTest_IllegalStateException() {
        // Arrange
        IllegalStateException exception = new IllegalStateException("test illegal state exception");

        // Act
        ResponseEntity<Object> response = exceptionHandlingAOP.handleControllerExceptions(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("test illegal state exception", response.getBody());
    }

    @Test
    void handleControllerExceptionsTest_OtherException() {
        Exception exception = new Exception("test other exception");
        ResponseEntity<Object> response = exceptionHandlingAOP.handleControllerExceptions(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred.", response.getBody());
    }


}