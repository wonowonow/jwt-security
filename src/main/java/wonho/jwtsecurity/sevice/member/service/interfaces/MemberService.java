package wonho.jwtsecurity.sevice.member.service.interfaces;

import wonho.jwtsecurity.sevice.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.sevice.member.dto.res.MemberResponseDto;

public interface MemberService {
    MemberResponseDto signUp(MemberCreateRequestDto requestDto);
}
