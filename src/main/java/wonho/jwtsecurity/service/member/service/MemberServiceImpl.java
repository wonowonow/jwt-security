package wonho.jwtsecurity.service.member.service;

import static wonho.jwtsecurity.global.exception.ExceptionCode.DUPLICATED_USER;
import static wonho.jwtsecurity.global.exception.ExceptionCode.INVALID_REFRESH_TOKEN;
import static wonho.jwtsecurity.global.exception.ExceptionCode.NOT_FOUND_REFRESH_TOKEN;
import static wonho.jwtsecurity.global.exception.ExceptionCode.NOT_FOUND_ROLE;
import static wonho.jwtsecurity.global.exception.ExceptionCode.NOT_FOUND_USER;
import static wonho.jwtsecurity.global.exception.ExceptionCode.NOT_MATCH_PASSWORD;
import static wonho.jwtsecurity.service.member.domain.AuthorityEnum.ROLE_USER;

import io.jsonwebtoken.Claims;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wonho.jwtsecurity.global.exception.BusinessException;
import wonho.jwtsecurity.global.jwt.JwtUtil;
import wonho.jwtsecurity.service.member.domain.Member;
import wonho.jwtsecurity.service.member.domain.repository.MemberRepository;
import wonho.jwtsecurity.service.member.domain.repository.RefreshTokenRepository;
import wonho.jwtsecurity.service.member.domain.repository.UserRoleRepository;
import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.req.MemberLoginRequestDto;
import wonho.jwtsecurity.service.member.dto.res.AllTokenResponseDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.service.member.service.interfaces.MemberService;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public MemberResponseDto signUp(MemberCreateRequestDto requestDto) {

        if (memberRepository.existsByUsername(requestDto.username())) {
            throw new BusinessException(DUPLICATED_USER);
        }
        Member member = Member.of(requestDto.username(),
                passwordEncoder.encode(requestDto.password()), requestDto.nickname(),
                new HashSet<>());

        member.addUserRole(userRoleRepository.findByAuthority(ROLE_USER)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ROLE)));

        return MemberResponseDto.from(memberRepository.save(member));
    }

    @Override
    public AllTokenResponseDto sign(MemberLoginRequestDto requestDto) {

        Member member = memberRepository.findByUsername(requestDto.username())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        if (!passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new BusinessException(NOT_MATCH_PASSWORD);
        }

        log.info(requestDto.password());

        String token = jwtUtil.createToken(member.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(member.getUsername());
        refreshTokenRepository.save(member.getUsername(), refreshToken);

        return AllTokenResponseDto.of(token, refreshToken);
    }

    @Override
    public AllTokenResponseDto refreshToken(String refreshToken) {

        refreshToken = jwtUtil.substringToken(refreshToken);

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new BusinessException(INVALID_REFRESH_TOKEN);
        }

        Claims claims = jwtUtil.getUserInfoFromToken(refreshToken);
        String username = claims.get(jwtUtil.USERNAME, String.class);

        String findRefreshToken = refreshTokenRepository.findByUsername(username).orElseThrow(
                () -> new BusinessException(NOT_FOUND_REFRESH_TOKEN)
        );

        if (!findRefreshToken.equals(refreshToken)) {
            throw new BusinessException(INVALID_REFRESH_TOKEN);
        }

        if (!memberRepository.existsByUsername(username)) {
            throw new BusinessException(NOT_FOUND_USER);
        }

        String newRefreshToken = jwtUtil.createRefreshToken(username);

        return AllTokenResponseDto.of(jwtUtil.createToken(username), refreshTokenRepository.save(username, newRefreshToken));
    }
}
