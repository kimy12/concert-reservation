package kr.hhplus.be.server.api.token.domain.repository;

import kr.hhplus.be.server.api.token.infrastructure.repository.TokenRedisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenRedisRepository tokenRedisRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @DisplayName("waiting 토큰을 생성한다.")
    @Test
    void saveWaitingToken() {

        // given
        String tokenValue = "test-waiting-token";
        LocalDateTime currentTime = LocalDateTime.now();
        tokenRedisRepository.saveWaitingToken(tokenValue, currentTime);

        // when
        boolean exists = tokenRedisRepository.isWaitingTokenExists(tokenValue);

        // then
        assertThat(exists).isTrue();
    }

    @DisplayName("active 토큰을 생성한다.")
    @Test
    void saveActiveToken() {

        // given
        String tokenValue = "00007048-860d-babe-9971-cec93ce70111";
        LocalDateTime currentTime = LocalDateTime.now();
        tokenRedisRepository.saveActiveToken(tokenValue, currentTime);

        // when
        String activeKey = tokenRedisRepository.getActiveTokenKeyByValue(tokenValue);

        // then
        assertThat(activeKey).isNotNull();
        assertThat(tokenRedisRepository.isActiveTokenExists(activeKey, tokenValue)).isTrue();
    }

    @DisplayName("waiting 토큰을 삭제한다.")
    @Test
    void removeWaitingToken() {

        // given
        String tokenValue = "test-remove-token";
        LocalDateTime currentTime = LocalDateTime.now();
        tokenRedisRepository.saveWaitingToken(tokenValue, currentTime);

        // when
        tokenRedisRepository.removeWaitingToken(tokenValue);

        // then
        boolean exists = tokenRedisRepository.isWaitingTokenExists(tokenValue);
        assertThat(exists).isFalse();
    }

    @DisplayName("waiting 토큰을 가져온다.")
    @Test
    void getAllWaitingKeysOrdered() {
        // given
        tokenRedisRepository.saveWaitingToken("token1", LocalDateTime.now().minusMinutes(5));
        tokenRedisRepository.saveWaitingToken("token2", LocalDateTime.now().minusMinutes(2));

        // when
        Set<String> tokens = tokenRedisRepository.getAllWaitingKeysOrdered();

        // then
        assertThat(tokens).containsExactly("token1", "token2");
    }

    @DisplayName("Active 토큰의 존재를 확인한다.")
    @Test
    void checkActiveToken() {
        // given
        String tokenValue = "test-active-token";
        LocalDateTime time = LocalDateTime.now();
        tokenRedisRepository.saveActiveToken(tokenValue, time);

        // when
        tokenRedisRepository.saveActiveToken(tokenValue, time);
        String activeKey = tokenRedisRepository.getActiveTokenKeyByValue(tokenValue);
        assertThat(activeKey).isNotNull();

        // then
        assertThat(tokenRedisRepository.isActiveTokenExists(activeKey, tokenValue)).isTrue();
    }

}