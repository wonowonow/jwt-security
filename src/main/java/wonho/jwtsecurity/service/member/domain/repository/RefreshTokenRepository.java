package wonho.jwtsecurity.service.member.domain.repository;

import java.util.Optional;

public interface RefreshTokenRepository {

    String save(String username, String refreshToken);

    Optional<String> findByUsername(String username);
}
