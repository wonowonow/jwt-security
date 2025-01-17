package wonho.jwtsecurity.sevice.member.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wonho.jwtsecurity.sevice.member.domain.Member;
import wonho.jwtsecurity.sevice.member.domain.repository.MemberRepository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long>, MemberRepository {

}
