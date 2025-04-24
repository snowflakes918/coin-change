package com.sample.coinchange.aop;

import com.sample.coinchange.dto.ResponseType;
import com.sample.coinchange.dto.ResultEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

        ResponseType<Object> response = exceptionHandlingAOP.handleIllegalArgumentException(exception);

        // Assert
        assertEquals(ResultEnum.BAD_INPUT.getCode(), response.getCode());
        assertEquals("test illegal argument exception", response.getMessage());
    }

    @Test
    void handleControllerExceptionsTest_IllegalStateException() {
        // Arrange
        IllegalStateException exception = new IllegalStateException("test illegal state exception");

        // Act
        ResponseType<Object> response = exceptionHandlingAOP.handleIllegalStateException(exception);

        // Assert
        assertEquals(ResultEnum.FAILED.getCode(), response.getCode());
        assertEquals("test illegal state exception", response.getMessage());
    }

    @Test
    void handleControllerExceptionsTest_OtherException() {
        Exception exception = new Exception("test other exception");
        ResponseType<Object> response = exceptionHandlingAOP.handleOtherException(exception);

        assertEquals(ResultEnum.ERROR.getCode(), response.getCode());
        assertEquals("test other exception", response.getMessage());
    }


}