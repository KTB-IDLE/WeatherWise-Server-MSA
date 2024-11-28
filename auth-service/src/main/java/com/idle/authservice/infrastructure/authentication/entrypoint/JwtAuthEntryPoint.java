package com.idle.authservice.infrastructure.authentication.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.commonservice.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthEntryPoint() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // JavaTimeModule 등록
    }

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        final ErrorCode errorCode = (ErrorCode) request.getAttribute("exception");

        // 에러 코드가 없으면 기본 에러 코드를 사용
        if (errorCode == null) {
            setErrorResponse(response, ErrorCode.NOT_END_POINT);
            return;
        }

        // 에러 코드가 있으면 해당 코드로 응답 설정
        setErrorResponse(response, errorCode);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        // ErrorResponse 객체 생성
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        // 응답 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());

        // ErrorResponse 객체를 JSON 형식으로 변환하여 응답에 작성
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
