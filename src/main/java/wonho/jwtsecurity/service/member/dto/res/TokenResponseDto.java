package wonho.jwtsecurity.service.member.dto.res;

import static lombok.AccessLevel.*;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = PRIVATE)
public record TokenResponseDto(
        String token
) {

    public static TokenResponseDto from(String token) {

        return TokenResponseDto.builder()
                .token(token)
                .build();
    }
}
