package wonho.jwtsecurity.global.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.StringUtils;

@TestInstance(Lifecycle.PER_CLASS)
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String secretKey;

    @BeforeAll
    void setUp() {
        secretKey = "wonho1234123412341234123412341234123412341234123";
        jwtUtil = new JwtUtil(secretKey);
    }

    @Test
    @DisplayName("토큰 생성 테스트")
    void createToken() {
        // Given
        String username = "testUser";

        // When
        String token = jwtUtil.createToken(username);

        // Then
        assertTrue(StringUtils.hasText(token));
        assertTrue(token.startsWith("eyJ")); // JWT 토큰은 항상 "eyJ"로 시작
    }

    @Test
    @DisplayName("유효 토큰 테스트 - 유효한 토큰")
    void validateToken_success() {
        // Given
        String username = "testUser";
        String token = jwtUtil.createToken(username);

        // When
        boolean isValid = jwtUtil.validateToken(token);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("유효 토큰 테스트 - 만료된 토큰")
    void validateToken_expired() {
        // Given
        String expiredToken = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 60 * 60 * 1000)) // 1시간 전
                .setExpiration(new Date(System.currentTimeMillis() - 30 * 60 * 1000)) // 30분 전 만료
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)), SignatureAlgorithm.HS256)
                .compact();

        // When / Then
        JwtException exception = assertThrows(JwtException.class, () -> jwtUtil.validateToken(expiredToken));
        assertEquals("ExceptionCode.TOKEN_EXPIRED", exception.getMessage());
    }

    @Test
    @DisplayName("토큰 유저 정보 추출")
    void getUserInfoFromToken() {
        // Given
        String username = "testUser";
        String token = jwtUtil.createToken(username);

        // When
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // Then
        assertEquals(username, claims.get("Username"));
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    @DisplayName("Http Request 에서 Token 추출")
    void getTokenFromRequest() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = jwtUtil.createToken("testUser");
        request.addHeader("Authorization", "Bearer " + token);

        // When
        String extractedToken = jwtUtil.getTokenFromRequest(request);

        // Then
        assertEquals(token, extractedToken);
    }

    @Test
    @DisplayName("토큰 substring 테스트 - Success")
    void substringToken_Success() {
        // Given
        String token = "Bearer 펩시 콜라 짱짱맨";

        // When
        String extractedToken = jwtUtil.substringToken(token);

        // Then
        assertEquals("펩시 콜라 짱짱맨", extractedToken);
    }

    @Test
    @DisplayName("토큰 substring 테스트 - invalid")
    void substringToken_invalid() {
        // Given
        String token = "진짜진짜_이상한_토큰";

        // When & Then
        JwtException exception = assertThrows(JwtException.class, () -> jwtUtil.substringToken(token));
        assertEquals("ExceptionCode.NOT_FOUND_TOKEN", exception.getMessage());
    }
}