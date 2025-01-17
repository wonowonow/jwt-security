package wonho.jwtsecurity.sevice.member.service;

import static wonho.jwtsecurity.sevice.member.domain.AuthorityEnum.ROLE_USER;

import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wonho.jwtsecurity.sevice.member.domain.Member;
import wonho.jwtsecurity.sevice.member.domain.repository.MemberRepository;
import wonho.jwtsecurity.sevice.member.domain.repository.UserRoleRepository;
import wonho.jwtsecurity.sevice.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.sevice.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.sevice.member.service.interfaces.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    @Override
    public MemberResponseDto signUp(MemberCreateRequestDto requestDto) {

        if (memberRepository.findByUsername(requestDto.username()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }
        Member member = Member.of(requestDto.username(), passwordEncoder.encode(requestDto.password()), requestDto.nickname(), new HashSet<>());
        member.addUserRole(userRoleRepository.findByAuthority(ROLE_USER).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 권한입니다.")));

        return MemberResponseDto.from(memberRepository.save(member));
    }
}
