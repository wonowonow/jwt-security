package wonho.jwtsecurity.service.member.dto.res;

import static lombok.AccessLevel.PRIVATE;

import lombok.Builder;

@Builder(access = PRIVATE)
public record AllTokenResponseDto(
        String token,
        String refreshToken
) {

    public static AllTokenResponseDto of(String token, String refreshToken) {

        return AllTokenResponseDto.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
