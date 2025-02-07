package com.idle.commonservice.auth;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
@Getter
@Slf4j
@AllArgsConstructor
public enum ERole {
    GUEST("ROLE_GUEST", "GUEST"), USER("ROLE_USER", "USER"), ADMIN("ROLE_ADMIN", "ADMIN");

    private final String roleCode;
    private final String displayName;

    public static ERole of(String roleCode) {
        return Arrays.stream(ERole.values())
                .filter(v -> v.getRoleCode().equals("ROLE_" + roleCode) || v.getRoleCode().equals(roleCode))
                .findAny()
                .orElseThrow(() -> new BaseException(ErrorCode.ACCESS_DENIED_ERROR));
    }
}
