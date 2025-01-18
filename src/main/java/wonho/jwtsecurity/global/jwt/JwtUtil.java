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
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final Long EXPIRATION_TIME = 60 * 60 * 1000L;
    private static final String USERNAME = "Username";

    /**
     * Initializes the JWT signing key by decoding the base64-encoded secret.
     *
     * This method is annotated with {@code @PostConstruct} to ensure it is called automatically
     * after the bean has been constructed and dependencies have been injected. It decodes the
     * base64-encoded secret and generates an HMAC-SHA key for JWT token signing and verification.
     *
     * @throws IllegalArgumentException if the secret cannot be decoded or is invalid
     */
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * Generates a JSON Web Token (JWT) for the given username.
     *
     * @param username the username to include in the token's claims
     * @return a complete JWT string prefixed with "Bearer ", including headers, claims, and signature
     *
     * @throws IllegalArgumentException if the username is null or empty
     *
     * The token includes:
     * - A header specifying token type (JWT) and algorithm
     * - A claim containing the username
     * - Issued at timestamp
     * - Expiration timestamp (set to a predefined duration from issue time)
     * - Signed with the configured cryptographic key
     */
    public String createToken(String username) {

        Date date = new Date();

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", signatureAlgorithm.getValue());

        return BEARER +
                Jwts.builder()
                        .setHeader(header)
                        .claim(USERNAME, username)
                        .setIssuedAt(date)
                        .setExpiration(new Date(date.getTime() + EXPIRATION_TIME))
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    /**
     * Validates the provided JSON Web Token (JWT).
     *
     * @param token the JWT to validate
     * @return true if the token is valid
     * @throws JwtException if the token is invalid, with specific error codes:
     *         - TOKEN_INVALID for security, malformed, or signature-related issues
     *         - TOKEN_EXPIRED for expired tokens
     *         - TOKEN_UNSUPPORTED for unsupported token formats
     *         - TOKEN_EMPTY for null or empty tokens
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            throw new JwtException("ExceptionCode.TOKEN_INVALID");
        } catch (ExpiredJwtException e) {
            throw new JwtException("ExceptionCode.TOKEN_EXPIRED");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("ExceptionCode.TOKEN_UNSUPPORTED");
        } catch (IllegalArgumentException e) {
            throw new JwtException("ExceptionCode.TOKEN_EMPTY");
        }
    }

    /**
     * Extracts and returns the claims (payload) from a JSON Web Token (JWT).
     *
     * @param token The JWT from which to extract claims
     * @return Claims object containing the token's payload information
     * @throws JwtException if the token is invalid or cannot be parsed
     */
    public Claims getUserInfoFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the actual token from a bearer token string.
     *
     * @param token The full bearer token string to process
     * @return The token without the "Bearer " prefix
     * @throws JwtException if the token is empty or does not start with "Bearer "
     */
    public String substringToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER)) {
            return token.substring(BEARER.length());
        } else {
            throw new JwtException("ExceptionCode.NOT_FOUND_TOKEN");
        }
    }

    /**
     * Retrieves the JWT from the Authorization header of an HTTP request.
     *
     * @param req the HttpServletRequest containing the Authorization header
     * @return the extracted JWT token without the "Bearer " prefix
     * @throws JwtException if the token is invalid or not found in the request header
     */
    public String getTokenFromRequest(HttpServletRequest req) {
        String token = req.getHeader(AUTHORIZATION_HEADER);

        return substringToken(token);
    }
}
