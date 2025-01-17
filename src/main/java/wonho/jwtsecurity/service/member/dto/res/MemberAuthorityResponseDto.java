package wonho.jwtsecurity.service.member.dto.res;

import static lombok.AccessLevel.*;

import lombok.Builder;
import wonho.jwtsecurity.service.member.domain.MemberUserRole;

@Builder(access = PRIVATE)
public record MemberAuthorityResponseDto(
        String authorityName
) {

    public static MemberAuthorityResponseDto from(MemberUserRole userRoles) {

        return MemberAuthorityResponseDto.builder()
                .authorityName(userRoles.getAuthority().getAuthority().name()).build();
    }
}
