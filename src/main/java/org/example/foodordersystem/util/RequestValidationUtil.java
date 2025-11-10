package org.example.foodordersystem.util;

import org.example.foodordersystem.web.exception.ParamsValidationDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.Collection;

public class RequestValidationUtil {

    public static ProblemDetail getProblemDetailByValidationDetails(Collection<ParamsValidationDetails> validationDetails) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request Validation Failed");
        problemDetail.setType(URI.create("urn:problem-type:validation-error"));
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("validationDetails", validationDetails);
        return problemDetail;
    }
}
