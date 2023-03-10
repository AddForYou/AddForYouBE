package com.example.addforyou.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private int code;

    private String errorSimpleName;

    private String msg;

    private LocalDateTime timestamp;

    public ErrorMessage(Exception exception, HttpStatus status) {
        this.code = status.value();
        this.errorSimpleName = exception.getLocalizedMessage();
        this.msg = exception.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorMessage of(Exception exception, HttpStatus status) {
        return new ErrorMessage(exception, status);
    }
}
