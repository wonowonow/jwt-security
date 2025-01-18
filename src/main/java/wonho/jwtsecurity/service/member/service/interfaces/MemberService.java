package wonho.jwtsecurity.service.member.service.interfaces;

import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.req.MemberLoginRequestDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.service.member.dto.res.TokenResponseDto;

public interface MemberService {
    MemberResponseDto signUp(MemberCreateRequestDto requestDto);

    TokenResponseDto sign(MemberLoginRequestDto requestDto);

    /**
     * 리프레시 토큰을 통해 액세스 토큰을 발급 받는 로직입니다.
     * 1. JWT 해독
     * 2. JWT 기준 Username 으로 Refresh DB 서치
     * 3. 찾은 Refresh Token 과 Request 로 들어온 Refresh Token 이 일치 하는 지 확인
     * 4. Username 으로 Member 서치
     * 5. Access Token 반환
     * @param refreshToken
     * @return
     */
    String refreshToken(String refreshToken);
}
