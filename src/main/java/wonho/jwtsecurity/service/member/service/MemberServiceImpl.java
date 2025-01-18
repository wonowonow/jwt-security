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

    /**
     * Registers a new member in the system.
     *
     * @param requestDto The data transfer object containing member registration details
     * @return A response DTO representing the newly created member
     * @throws IllegalArgumentException If the username already exists or the default user role cannot be found
     */
    @Override
    public MemberResponseDto signUp(MemberCreateRequestDto requestDto) {

        if (memberRepository.findByUsername(requestDto.username()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }
        Member member = Member.of(requestDto.username(), passwordEncoder.encode(requestDto.password()), requestDto.nickname(), new HashSet<>());
        member.addUserRole(userRoleRepository.findByAuthority(ROLE_USER).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 권한입니다.")));

        return MemberResponseDto.from(memberRepository.save(member));
    }

    /**
     * Authenticates a member and generates a JWT token for successful login.
     *
     * @param requestDto The login request containing username and password
     * @return A TokenResponseDto containing the generated JWT token
     * @throws IllegalArgumentException If the username does not exist or the password is incorrect
     */
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
