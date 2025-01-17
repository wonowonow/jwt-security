package wonho.jwtsecurity.service.member.service;

import static wonho.jwtsecurity.service.member.domain.AuthorityEnum.ROLE_USER;

import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wonho.jwtsecurity.global.jwt.JwtUtil;
import wonho.jwtsecurity.service.member.domain.Member;
import wonho.jwtsecurity.service.member.domain.repository.MemberRepository;
import wonho.jwtsecurity.service.member.domain.repository.UserRoleRepository;
import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.req.MemberLoginRequestDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.service.member.dto.res.TokenResponseDto;
import wonho.jwtsecurity.service.member.service.interfaces.MemberService;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final JwtUtil jwtUtil;

    @Override
    public MemberResponseDto signUp(MemberCreateRequestDto requestDto) {

        if (memberRepository.findByUsername(requestDto.username()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }
        Member member = Member.of(requestDto.username(), passwordEncoder.encode(requestDto.password()), requestDto.nickname(), new HashSet<>());
        member.addUserRole(userRoleRepository.findByAuthority(ROLE_USER).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 권한입니다.")));

        return MemberResponseDto.from(memberRepository.save(member));
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponseDto sign(MemberLoginRequestDto requestDto) {

        Member member = memberRepository.findByUsername(requestDto.username())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));

        if (!passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return TokenResponseDto.from(jwtUtil.createToken(member.getUsername()));
    }
}
