package kr.hhplus.be.server.api.token.infrastructure.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private ZSetOperations<String, String> zSetOperations;
    private SetOperations<String, String> setOperations;

    @Value("${custom.redis.token.waiting-key}")
    private String waitingTokenKey;

    @Value("${custom.redis.token.active-key}")
    private String activeTokenKey;

    @Value("${custom.redis.token.ttl-seconds}")
    private long activeTokenTtlSeconds;


    @PostConstruct
    private void init() {
        zSetOperations = redisTemplate.opsForZSet();
        setOperations = redisTemplate.opsForSet();
    }

    public void saveWaitingToken(String value, LocalDateTime time) {
        long parseTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        zSetOperations.add(waitingTokenKey, value, parseTime);
    }

    public boolean isWaitingTokenExists(String value) {
        Double score = zSetOperations.score(waitingTokenKey, value);
        return score != null;
    }

    public void removeWaitingToken(String key) {
        zSetOperations.remove(waitingTokenKey, key);
    }

    public Set<String> getAllWaitingKeysOrdered() {
        return zSetOperations.rangeByScore(waitingTokenKey, 0, Double.MAX_VALUE, 0, 50);
    }

    public void saveActiveToken(String value, LocalDateTime time) {
        long parseTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        setOperations.add(activeTokenKey+parseTime, value);
        if (redisTemplate.getExpire(activeTokenKey+parseTime) == -1) {
            redisTemplate.expire(activeTokenKey+parseTime, Duration.ofSeconds(activeTokenTtlSeconds));
        }
    }

    public String getActiveTokenKeyByValue(String value) {
        Set<String> keys = redisTemplate.keys(activeTokenKey + "*");
        for (String key : keys) {
            if (Boolean.TRUE.equals(setOperations.isMember(key, value))) {
                return key;
            }
        }
        return null;
    }

    public boolean isActiveTokenExists(String activeTokenKetName, String value) {
        return Boolean.TRUE.equals(setOperations.isMember(activeTokenKetName, value));
    }
}
