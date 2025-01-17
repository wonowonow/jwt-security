package wonho.jwtsecurity.sevice.member.domain.repository;

import java.util.Optional;
import wonho.jwtsecurity.sevice.member.domain.AuthorityEnum;
import wonho.jwtsecurity.sevice.member.domain.UserRole;

public interface UserRoleRepository {

    Optional<UserRole> findByAuthority(AuthorityEnum authority);
}
