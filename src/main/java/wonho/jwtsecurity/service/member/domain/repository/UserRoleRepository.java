package wonho.jwtsecurity.service.member.domain.repository;

import java.util.Optional;
import wonho.jwtsecurity.service.member.domain.AuthorityEnum;
import wonho.jwtsecurity.service.member.domain.UserRole;

public interface UserRoleRepository {

    Optional<UserRole> findByAuthority(AuthorityEnum authority);
}
