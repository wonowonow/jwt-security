package wonho.jwtsecurity.global.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import wonho.jwtsecurity.global.security.UserDetailsServiceImpl;

@RequiredArgsConstructor
public class AccessTokenFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.getTokenFromRequest(request);

        if (StringUtils.hasText(token)) {
            token = jwtUtil.substringToken(token);

            if (!jwtUtil.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("유효하지 않은 액세스 토큰입니다.");
                return;
            }

            Claims claims = jwtUtil.getUserInfoFromToken(token);
            String username = String.valueOf(claims.get(jwtUtil.USERNAME));

            setAuthentication(username);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }
}
