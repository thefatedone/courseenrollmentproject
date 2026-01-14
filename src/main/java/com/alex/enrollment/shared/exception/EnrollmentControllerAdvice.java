package com.alex.enrollment.shared.exception;

import com.alex.enrollment.shared.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class EnrollmentControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                MDC.get("requestId"),
                HttpStatus.NOT_FOUND,
                request.getRequestURI(),
                e.getMessage());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                MDC.get("requestId"),
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                e.getMessage());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
