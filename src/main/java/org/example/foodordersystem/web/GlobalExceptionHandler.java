package org.example.foodordersystem.web;

import lombok.extern.slf4j.Slf4j;
import org.example.foodordersystem.service.exception.ResourceNotFoundException;
import org.example.foodordersystem.util.ProblemDetailBuilder;
import org.example.foodordersystem.web.exception.ParamsValidationDetails;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.info("Validation Error has occurred");
        List<ParamsValidationDetails> validationDetails = ex.getFieldErrors().stream()
                .map(fieldError -> ParamsValidationDetails.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build()
                )
                .toList();

        ProblemDetail problemDetail = ProblemDetailBuilder.builder()
                .status(HttpStatus.BAD_REQUEST)
                .type("urn:problem-type:validation-error")
                .title("Validation Error")
                .detail("Request Validation Failed")
                .property("validationDetails", validationDetails)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        log.info("Resource Not Found has occurred");

        ProblemDetail problemDetail = ProblemDetailBuilder.builder()
                .status(HttpStatus.NOT_FOUND)
                .type("urn:problem-type:resource-not-found")
                .title("Resource Not Found")
                .detail(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }
}
