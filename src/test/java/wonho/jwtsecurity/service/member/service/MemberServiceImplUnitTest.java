package wonho.jwtsecurity.service.member.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import wonho.jwtsecurity.service.member.domain.AuthorityEnum;
import wonho.jwtsecurity.service.member.domain.Member;
import wonho.jwtsecurity.service.member.domain.MemberUserRole;
import wonho.jwtsecurity.service.member.domain.UserRole;
import wonho.jwtsecurity.service.member.domain.repository.MemberRepository;
import wonho.jwtsecurity.service.member.domain.repository.UserRoleRepository;
import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;

class MemberServiceImplUnitTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void signUp_success() {
        // Given
        String username = "testUser";
        String password = "rawPassword";
        String encodedPassword = "encodedPassword";
        String nickname = "testNickname";
        Set<MemberUserRole> userRoles = new HashSet<>();
        Member member = Member.of(username, encodedPassword, nickname, userRoles);
        UserRole userRole = UserRole.from(AuthorityEnum.ROLE_USER);
        member.addUserRole(userRole);
        MemberCreateRequestDto requestDto = new MemberCreateRequestDto(username, password, nickname);

        when(memberRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRoleRepository.findByAuthority(AuthorityEnum.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        MemberResponseDto response = memberService.signUp(requestDto);

        // Then
        assertNotNull(response);
        assertEquals(username, response.username());
        assertEquals(nickname, response.nickname());
        assertEquals(userRoles.size(), response.authorities().size());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 - 중복 아이디")
    void signUp_fail_duplicateUsername() {
        // Given
        String username = "duplicateUser";
        String password = "password";
        String nickname = "nickname";

        MemberCreateRequestDto requestDto = new MemberCreateRequestDto(username, password, nickname);

        when(memberRepository.existsByUsername(username)).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.signUp(requestDto));
        assertEquals("이미 존재하는 아이디 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 - 권한 없음")
    void signUp_fail_missingRole() {
        // Given
        String username = "testUser";
        String password = "password";
        String nickname = "nickname";

        MemberCreateRequestDto requestDto = new MemberCreateRequestDto(username, password, nickname);

        when(memberRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRoleRepository.findByAuthority(AuthorityEnum.ROLE_USER)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.signUp(requestDto));
        assertEquals("존재하지 않는 권한입니다.", exception.getMessage());
    }
}