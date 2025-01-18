package wonho.jwtsecurity.service.member.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberEntityUnitTest {

    @Test
    @DisplayName("멤버 생성 테스트")
    void createMember() {
        // Given
        String username = "username";
        String password = "password";
        String nickname = "nickname";
        Set<MemberUserRole> userRoles = new HashSet<>();
        // When
        Member member = Member.of(username, password, nickname, userRoles);
        // Then
        assertNotNull(member);
        assertEquals(member.getUsername(), username);
        assertEquals(member.getPassword(), password);
        assertEquals(member.getNickname(), nickname);
        assertEquals(member.getUserRoles(), userRoles);
        assertEquals(member.getUserRoles().size(), 0);
    }

    @Test
    @DisplayName("멤버 권한 추가 테스트")
    void addUserRole() {
        // Given
        String username = "username";
        String password = "password";
        String nickname = "nickname";
        Set<MemberUserRole> userRoles = new HashSet<>();
        Member member = Member.of(username, password, nickname, userRoles);
        UserRole userRole = UserRole.from(AuthorityEnum.ROLE_USER);
        // When
        member.addUserRole(userRole);

        // Then
        assertEquals(member.getUserRoles().size(), 1);
        assertTrue(member.getUserRoles().stream().anyMatch(memberUserRole -> memberUserRole.getAuthority().equals(userRole)));
    }
}