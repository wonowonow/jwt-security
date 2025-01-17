package wonho.jwtsecurity.service.member.service.interfaces;

import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.req.MemberLoginRequestDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.service.member.dto.res.TokenResponseDto;

public interface MemberService {
    MemberResponseDto signUp(MemberCreateRequestDto requestDto);

    TokenResponseDto sign(MemberLoginRequestDto requestDto);
}
