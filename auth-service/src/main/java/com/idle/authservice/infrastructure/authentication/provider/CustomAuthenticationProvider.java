package com.idle.authservice.infrastructure.authentication.provider;

import com.idle.authservice.infrastructure.authentication.CustomUserDetails;
import com.idle.authservice.infrastructure.authentication.service.CustomUserDetailService;
import com.idle.authservice.infrastructure.info.JwtUserInfo;
import com.idle.commonservice.auth.ERole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;

    /**
     * 인증 구현
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 전달 받은 UsernamePasswordAuthenticationToken
        JwtUserInfo jwtUserInfo = (JwtUserInfo) authentication.getPrincipal();

        CustomUserDetails userPrincipal = (CustomUserDetails) customUserDetailService.loadUserById(jwtUserInfo.id());

        if (userPrincipal.getRole() != jwtUserInfo.role() && userPrincipal.getRole() != ERole.ADMIN) {
            throw new AuthenticationException("Invalid Role") {};
        }

        // 인증 성공 시 UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
