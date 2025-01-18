package wonho.jwtsecurity.service.member.dto.res;

import static lombok.AccessLevel.PRIVATE;

import lombok.Builder;

@Builder(access = PRIVATE)
public record TokenResponseDto(
        String token,
        String refreshToken
) {

    public static TokenResponseDto of(String token, String refreshToken) {

        return TokenResponseDto.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
