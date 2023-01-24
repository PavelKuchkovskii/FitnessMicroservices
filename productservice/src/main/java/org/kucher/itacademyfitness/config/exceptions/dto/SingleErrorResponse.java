package org.kucher.itacademyfitness.config.exceptions.dto;

public class SingleErrorResponse {
    private String logref;
    private String message;

    public SingleErrorResponse() {
    }

    public SingleErrorResponse(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public void setLogref(String logref) {
        this.logref = logref;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
