package wonho.jwtsecurity.service.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import wonho.jwtsecurity.service.member.domain.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRedisRepository implements RefreshTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final String PREFIX = "refreshToken:";

    @Override
    public String save(String username, String refreshToken) {

        String key = PREFIX + username;
        redisTemplate.opsForValue().set(key, refreshToken);

        return refreshToken;
    }

    @Override
    public String findByUsername(String username) {

        String key = PREFIX + username;

        return redisTemplate.opsForValue().get(key);
    }
}
