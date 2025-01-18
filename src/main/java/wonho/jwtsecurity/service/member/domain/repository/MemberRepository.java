package wonho.jwtsecurity.service.member.domain.repository;

import java.util.Optional;
import wonho.jwtsecurity.service.member.domain.Member;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
