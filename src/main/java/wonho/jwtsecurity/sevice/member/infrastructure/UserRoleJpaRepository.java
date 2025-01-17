package wonho.jwtsecurity.sevice.member.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wonho.jwtsecurity.sevice.member.domain.AuthorityEnum;
import wonho.jwtsecurity.sevice.member.domain.UserRole;
import wonho.jwtsecurity.sevice.member.domain.repository.UserRoleRepository;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long>, UserRoleRepository {

    Optional<UserRole> findByAuthority(AuthorityEnum authority);
}
