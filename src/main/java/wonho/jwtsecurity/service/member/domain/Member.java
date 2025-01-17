package wonho.jwtsecurity.service.member.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 15)
    private String nickname;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MemberUserRole> userRoles = new HashSet<>();

    @Builder(access = PRIVATE)
    private Member(String username, String password, String nickname,
            Set<MemberUserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userRoles = userRoles;
    }

    public static Member of(String username, String password, String nickname,
            Set<MemberUserRole> userRoles) {

        return Member.builder()
                     .username(username)
                     .password(password)
                     .nickname(nickname)
                     .userRoles(userRoles)
                     .build();
    }

    public void addUserRole(UserRole userRole) {
        this.userRoles.add(MemberUserRole.of(this, userRole));
    }
}
