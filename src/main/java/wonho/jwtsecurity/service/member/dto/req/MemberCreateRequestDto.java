package wonho.jwtsecurity.service.member.dto.req;

public record MemberCreateRequestDto (
        String username,
        String password,
        String nickname
) {

}
