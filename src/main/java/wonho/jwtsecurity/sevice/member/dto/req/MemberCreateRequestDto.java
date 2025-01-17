package wonho.jwtsecurity.sevice.member.dto.req;

public record MemberCreateRequestDto (
        String username,
        String password,
        String nickname
) {

}
