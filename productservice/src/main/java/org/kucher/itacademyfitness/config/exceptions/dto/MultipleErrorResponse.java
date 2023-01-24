package org.kucher.itacademyfitness.config.exceptions.dto;

import java.util.List;

public class MultipleErrorResponse {

    private String logref;
    private List<MultipleError> errors;

    public MultipleErrorResponse() {
    }

    public MultipleErrorResponse(String logref) {
        this.logref = logref;
    }

    public String getLogref() {
        return logref;
    }

    public void setLogref(String logref) {
        this.logref = logref;
    }

    public List<MultipleError> getErrors() {
        return errors;
    }

    public void setErrors(List<MultipleError> errors) {
        this.errors = errors;
    }
}
