package wonho.jwtsecurity.service.member.service.interfaces;

import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;

public interface MemberService {
    MemberResponseDto signUp(MemberCreateRequestDto requestDto);
}
