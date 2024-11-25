package com.idle.authservice.infrastructure.auth.util;


import com.idle.authservice.infrastructure.UserServiceClient;
import com.idle.authservice.infrastructure.auth.constant.Constants;
import com.idle.authservice.infrastructure.auth.dto.JwtTokenDto;
import com.idle.authservice.presentation.dto.response.UserResponse;
import com.idle.commonservice.auth.ERole;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil implements InitializingBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final UserServiceClient userServiceClient;

    // private final UserRepository userRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token-expire-period}")
    private Integer accessTokenExpiredPeriod;
    @Getter
    @Value("${jwt.refresh-token-expire-period}")
    private Integer refreshTokenExpiredPeriod;
    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public int getWebRefreshTokenExpirationSecond() {
        return (int) (refreshTokenExpiredPeriod / 1000);
    }

    public JwtTokenDto generateTokens(final Long id, final ERole role) {
        final String accessToken = generateToken(id, role, accessTokenExpiredPeriod * 1000);
        final String refreshToken = generateToken(id, role, refreshTokenExpiredPeriod * 1000);
        return new JwtTokenDto(accessToken, refreshToken);
    }

    public String generateToken(final Long id, final ERole role, final Integer expirationPeriod) {
        final Claims claims = Jwts.claims();
        claims.put(Constants.USER_ID_CLAIM_NAME, id.toString());
        claims.put(Constants.USER_ROLE_CLAIM_NAME, role.toString());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationPeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateJwtToken(Authentication authentication) {
        final Claims claims = Jwts.claims();
        claims.put(Constants.USER_ID_CLAIM_NAME, String.valueOf(authentication.getPrincipal()));
        claims.put(Constants.USER_ROLE_CLAIM_NAME, authentication.getAuthorities());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiredPeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims validateToken(final String token) throws ExpiredJwtException, JwtException {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    /**
     * Request Header에서 토큰 추출
     */
    public static String refineToken(HttpServletRequest request) throws JwtException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        } else {
            throw new IllegalArgumentException("Not Valid Or Not Exist Token");
        }

    }

/*    public JwtTokenDto reissue(final String refreshToken) {
        final Claims claims = validateToken(refreshToken);

        final Long id = Long.valueOf(claims.get(Constants.USER_ID_CLAIM_NAME).toString());
        final ERole role = ERole.of(claims.get(Constants.USER_ROLE_CLAIM_NAME).toString());

        UserResponse user = userServiceClient.findById(id);

        // final UserEntity user = userRepository.findByIdForLegacy(id).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        if (!Objects.equals(user.getId(), id) || user.getRole() != role || !user.getRefreshToken().equals(refreshToken)) {
            log.error("Invalid Token");
            throw new BaseException(ErrorCode.INVALID_TOKEN_ERROR);
        }

        final JwtTokenDto jwtTokenDto = generateTokens(id, role);

        user.updateRefreshToken(jwtTokenDto.getRefreshToken());

        return jwtTokenDto;
    }*/
}
