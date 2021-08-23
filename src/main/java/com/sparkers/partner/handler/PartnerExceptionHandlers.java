package com.sparkers.partner.handler;

import com.sparkers.partner.dto.ExceptionResponse;
import com.sparkers.partner.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class PartnerExceptionHandlers {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ExceptionResponse handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException exception) {
        return ExceptionResponse.builder()
                .message(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ExceptionResponse handleNotFoundException(Exception exception) {
        return ExceptionResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(value = RuntimeException.class )
    public ExceptionResponse handleRuntimeException(Exception exception) {
        return ExceptionResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }
}
