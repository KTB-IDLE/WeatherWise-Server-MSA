package com.idle.authservice.infrastructure.authentication.service;

import com.idle.authservice.infrastructure.UserServiceClient;
import com.idle.authservice.infrastructure.authentication.CustomUserDetails;
import com.idle.authservice.presentation.dto.UserSecurityForm;
import com.idle.authservice.presentation.dto.UserSecurityFormDto;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserSecurityFormDto userDto = userServiceClient.findUserIdAndRoleBySerialId(username);
        // UserSecurityForm 인터페이스로 변환
        UserSecurityForm user = UserSecurityFormDto.toSecurityForm(userDto);
        return CustomUserDetails.create(user);
    }
    public UserDetails loadUserById(Long userId) throws BaseException {
        UserSecurityFormDto userDto = userServiceClient.findByIdAndIsLoginAndRefreshTokenIsNotNull(userId, true)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        /*UserJpaRepository.UserSecurityForm user = userJpaRepository.findByIdAndIsLoginAndRefreshTokenIsNotNull(userId, true)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found UserId"));*/
        // UserSecurityForm 인터페이스로 변환
        UserSecurityForm user = UserSecurityFormDto.toSecurityForm(userDto);

        return CustomUserDetails.create(user);
    }
}