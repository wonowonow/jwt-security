package wonho.jwtsecurity.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wonho.jwtsecurity.global.jwt.AccessTokenFilter;
import wonho.jwtsecurity.global.jwt.JwtUtil;
import wonho.jwtsecurity.global.security.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public AccessTokenFilter accessTokenFilter() {
        return new AccessTokenFilter(userDetailsService, jwtUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .anyRequest().permitAll()

        );

        http.addFilterBefore(accessTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
