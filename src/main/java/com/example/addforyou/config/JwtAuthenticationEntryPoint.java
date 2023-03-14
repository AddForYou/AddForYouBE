package com.example.addforyou.config;

import com.example.addforyou.exception.member.UnauthorizedMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        Object errorObject = request.getAttribute("UnauthorizedMemberException");
        if (errorObject != null) {
            log.error("errorObject is not null!");
            sendErrorUnauthorized(response);
        } else {
            sendErrorDenied(response);
        }

    }

    private void sendErrorDenied(HttpServletResponse response) {

    }


    private void sendErrorUnauthorized(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json,charset=utf-8");

        makeResultResponse(
                new UnauthorizedMemberException("로그인이 필요합니다."),
                HttpStatus.UNAUTHORIZED
        );

    }

    private void makeResultResponse(UnauthorizedMemberException e, HttpStatus status) {
    }
}
