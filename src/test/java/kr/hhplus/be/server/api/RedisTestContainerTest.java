package kr.hhplus.be.server.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisTestContainerTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testRedisContainer() {
        // Redis에 값 저장 및 확인 테스트
        redisTemplate.opsForValue().set("test-key", "test-value");
        String value = redisTemplate.opsForValue().get("test-key");

        assertThat(value).isEqualTo("test-value");
    }
}
