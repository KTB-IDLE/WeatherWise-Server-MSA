package com.idle.authservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRefreshToken {
    private Long id;
    private String refreshToken;
}
