package com.example.addforyou.config;

import lombok.extern.slf4j.Slf4j;
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
        if(errorObject != null) {
            log.error("errorObject is not null!");
            sendErrorUnauthorize(response);
        } else {
            sendErrorDenied(response);
        }
        
    }

    private void sendErrorDenied(HttpServletResponse response) {
    }

    private void sendErrorUnauthorize(HttpServletResponse response) {
    }
}
