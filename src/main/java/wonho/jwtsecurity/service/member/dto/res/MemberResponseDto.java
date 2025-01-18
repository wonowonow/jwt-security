package wonho.jwtsecurity.service.member.dto.res;

import static lombok.AccessLevel.PRIVATE;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import wonho.jwtsecurity.service.member.domain.Member;

@Builder(access = PRIVATE)
public record MemberResponseDto (
        String username,
        String nickname,
        Set<MemberAuthorityResponseDto> authorities
) {

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .authorities(member.getUserRoles()
                        .stream()
                        .map(MemberAuthorityResponseDto::from)
                        .collect(Collectors.toSet()))
                .build();
    }
}
