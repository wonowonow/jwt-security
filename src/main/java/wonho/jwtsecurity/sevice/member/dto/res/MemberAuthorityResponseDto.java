package wonho.jwtsecurity.sevice.member.dto.res;

import static lombok.AccessLevel.*;

import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import wonho.jwtsecurity.sevice.member.domain.MemberUserRole;

@Builder(access = PRIVATE)
public record MemberAuthorityResponseDto(
        String authorityName
) {

    public static MemberAuthorityResponseDto from(MemberUserRole userRoles) {

        return MemberAuthorityResponseDto.builder()
                .authorityName(userRoles.getAuthority().getAuthority().name()).build();
    }
}
