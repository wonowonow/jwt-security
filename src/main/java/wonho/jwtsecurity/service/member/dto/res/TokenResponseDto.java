package wonho.jwtsecurity.service.member.dto.res;

import static lombok.AccessLevel.*;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = PRIVATE)
public record TokenResponseDto(
        String token
) {

    /**
     * Creates a new TokenResponseDto instance with the provided token.
     *
     * @param token the authentication token to be encapsulated in the response
     * @return a TokenResponseDto containing the specified token
     */
    public static TokenResponseDto from(String token) {

        return TokenResponseDto.builder()
                .token(token)
                .build();
    }
}
