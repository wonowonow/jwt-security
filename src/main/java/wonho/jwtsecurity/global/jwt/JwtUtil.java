package wonho.jwtsecurity.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtUtil {

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final Long EXPIRATION_TIME = 60 * 60 * 1000L;
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60L;
    public static final String USERNAME = "Username";

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] bytes = Base64.getDecoder().decode(secret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String username) {

        Date date = new Date();

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", signatureAlgorithm.getValue());

        return Jwts.builder()
                        .setHeader(header)
                        .setId(UUID.randomUUID().toString())
                        .setIssuer("jwt-security")
                        .setAudience("jwt-security-client")
                        .claim(USERNAME, username)
                        .setIssuedAt(date)
                        .setExpiration(new Date(date.getTime() + EXPIRATION_TIME))
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken(String username) {

        Date date = new Date();

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", signatureAlgorithm.getValue());

        return Jwts.builder()
                .setHeader(header)
                .setId(UUID.randomUUID().toString())
                .setIssuer("jwt-security")
                .setAudience("jwt-security-client")
                .claim(USERNAME, username)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error(e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            log.error(e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public Claims getUserInfoFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String substringToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER)) {
            return token.substring(BEARER.length());
        }
        return null;
    }

    public String getTokenFromRequest(HttpServletRequest req) {
        String token = req.getHeader(AUTHORIZATION_HEADER);

        return substringToken(token);
    }
}
