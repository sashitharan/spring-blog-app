package com.springboot.blog.springbootblogrestapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "ErrorDetails")


public class ErrorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails() {
    }

    public ErrorDetails(long id, Date timestamp, String message, String details) {
        this.id = id;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}


