package wonho.jwtsecurity.sevice.member.domain.repository;

import java.util.Optional;
import wonho.jwtsecurity.sevice.member.domain.Member;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByUsername(String username);
}
