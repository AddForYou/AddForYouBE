package com.example.addforyou.exception.member;

public class ForbiddenMemberException extends RuntimeException {
    public ForbiddenMemberException(String message) {
        super(message);
    }
}
