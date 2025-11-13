package org.example.foodordersystem.util;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;

//@Builder
public class ProblemDetailBuilder {
    private HttpStatus status;
    private String type;
    private String title;
    private String detail;

    public static ProblemDetailBuilder builder() {
        return new ProblemDetailBuilder();
    }

    public ProblemDetailBuilder status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ProblemDetailBuilder type(String type) {
        this.type = type;
        return this;
    }

    public ProblemDetailBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ProblemDetailBuilder detail(String detail) {
        this.detail = detail;
        return this;
    }

    public ProblemDetail build() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setType(URI.create(type));
        problemDetail.setTitle(title);
        return problemDetail;
    }
}
