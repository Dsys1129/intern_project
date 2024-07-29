package com.intern_project.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ErrorResponse {

    private Integer statusCode;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(Integer statusCode, String message, Map<String, String> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }
}
