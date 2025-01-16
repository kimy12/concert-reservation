package kr.hhplus.be.server.api.token.domain.model;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static kr.hhplus.be.server.api.token.domain.enums.TokenErrorCode.TOKEN_EXPIRED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class TokenModelTest {

    @DisplayName("토큰이 만료되면, 상태값을 바꿔주고 예외가 발생한다.")
    @Test
    void updateTokenStatusWithExpired() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.minusDays(1); // Token expired yesterday

        TokenModel token = TokenModel.builder()
                .expireAt(expiredAt)
                .tokenStatus(TokenStatus.ACTIVE)
                .build();

        // when // then
        assertThatThrownBy(() -> token.updateTokenStatus(now))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(TOKEN_EXPIRED.getMessage());

        assertEquals(TokenStatus.EXPIRED, token.getTokenStatus());
    }

    @DisplayName("토큰이 만료되지 않았으면 예외가 발생하지 않는다.")
    @Test
    void tokenIsStillValid() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validUntil = now.plusDays(1);

        TokenModel token = TokenModel.builder()
                .expireAt(validUntil)
                .build();

        // when // then
        assertDoesNotThrow(() -> token.updateTokenStatus(now));
        assertNotEquals(TokenStatus.EXPIRED, token.getTokenStatus());
    }

}