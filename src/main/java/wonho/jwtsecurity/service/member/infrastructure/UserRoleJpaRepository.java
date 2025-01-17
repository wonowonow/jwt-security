package wonho.jwtsecurity.service.member.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wonho.jwtsecurity.service.member.domain.AuthorityEnum;
import wonho.jwtsecurity.service.member.domain.UserRole;
import wonho.jwtsecurity.service.member.domain.repository.UserRoleRepository;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long>, UserRoleRepository {

    Optional<UserRole> findByAuthority(AuthorityEnum authority);
}
