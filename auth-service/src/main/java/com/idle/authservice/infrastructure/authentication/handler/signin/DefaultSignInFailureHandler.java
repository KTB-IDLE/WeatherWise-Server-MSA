package com.idle.authservice.infrastructure.authentication.handler.signin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.commonservice.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component @Slf4j
@RequiredArgsConstructor
public class DefaultSignInFailureHandler implements AuthenticationFailureHandler {

    @Value("${after-login.default-fail}")
    private String LOGIN_URL;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 실패 시 응답 설정
        log.info("로그인 실패");
        setFailureAppResponse(response);
    }

    private void setFailureAppResponse(HttpServletResponse response) throws IOException {
        // ErrorResponse 객체 생성
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.FAILURE_LOGIN);

        // 응답 헤더 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ErrorCode.FAILURE_LOGIN.getStatus().value());

        // ErrorResponse 객체를 JSON으로 변환하여 응답 본문에 작성
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
