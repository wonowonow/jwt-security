package wonho.jwtsecurity.service.member.domain.repository;

public interface RefreshTokenRepository {

    String save(String username, String refreshToken);

    String findByUsername(String username);
}
