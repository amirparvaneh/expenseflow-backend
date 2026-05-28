package com.expenseflow.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        ErrorCode errorCode = ex.getErrorCode();

        ErrorResponse response = new ErrorResponse(
                errorCode.getStatus().value(),
                errorCode.name(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                400,
                ErrorCode.VALIDATION_ERROR.name(),
                "Validation failed",
                request.getRequestURI()
        );

        List<ApiSubError> subErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapToSubError)
                .toList();

        response.setSubErrors(subErrors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                500,
                ErrorCode.INTERNAL_SERVER_ERROR.name(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.internalServerError().body(response);
    }

    private ApiSubError mapToSubError(FieldError fieldError) {
        return new ApiSubError(
                fieldError.getField(),
                fieldError.getDefaultMessage()
        );
    }
}