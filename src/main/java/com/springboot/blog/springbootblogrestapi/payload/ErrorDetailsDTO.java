package com.springboot.blog.springbootblogrestapi.payload;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetailsDTO {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetailsDTO(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ErrorDetailsDTO() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
