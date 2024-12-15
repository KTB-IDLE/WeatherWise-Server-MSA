package com.idle.authservice.infrastructure.event;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import com.idle.commonservice.model.Exp;
import com.idle.commonservice.model.Level;
import com.idle.commonservice.model.PersonalWeatherTraits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Builder @Getter
public class CreateUserEvent {
    private Long id; // 식별자

    private Exp exp; // 경험치

    private Level level; // 레벨

    private String  password; // 비밀번호

    private String  loginId; // 로그인 ID

    private PersonalWeatherTraits personalWeatherTraits; // 개인 날씨 특성

    private String nickname; // 닉네임

    private String serialId; // 소셜 ID

    private Boolean isLogin; // 로그인 상태

    private String refreshToken; // 리프레시 토큰

    private Boolean isDeleted = false; // 삭제 여부

    private Boolean isCompletedSurvey; // 설문 완료 여부

    private EProvider provider; // 소셜 로그인 제공자

    private ERole role; // 유저 역할

    /**
     * 회원가입용 (소셜)
     */
    public static CreateUserEvent signUpSocialLogin(String serialId, String password, EProvider provider, ERole role, String nicName) {
        return CreateUserEvent.builder()
                .serialId(serialId)
                .password(password)
                .provider(provider)
                .role(role)
                .exp(Exp.from(0))
                .level(Level.from(1))
                .nickname(nicName)
                .isLogin(true)
                .isDeleted(false)
                .build();
    }

}
