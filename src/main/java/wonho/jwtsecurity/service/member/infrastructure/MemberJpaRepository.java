package wonho.jwtsecurity.service.member.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wonho.jwtsecurity.service.member.domain.Member;
import wonho.jwtsecurity.service.member.domain.repository.MemberRepository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long>, MemberRepository {

    boolean existsByUsername(String username);
}
