package kr.hhplus.be.server.api.token.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.token.domain.model.TokenModel;
import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import kr.hhplus.be.server.api.token.domain.repository.TokenRepository;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;
import kr.hhplus.be.server.api.token.util.TokenUUIDManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static kr.hhplus.be.server.api.token.domain.enums.TokenErrorCode.TOKEN_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenUUIDManager tokenUUIDManager;

    @DisplayName("토큰 정보를 저장한다.")
    @Test
    void saveTokenInfo() {
        // given
        long userId = 12345L;
        LocalDateTime createdAt = LocalDateTime.now();
        UUID mockUuid = UUID.randomUUID();

        when(tokenUUIDManager.createUuidWithLong(userId)).thenReturn(mockUuid);

        // when
        UUID result = tokenService.saveTokenInfo(userId, createdAt);

        // then
        assertThat(result).isEqualTo(mockUuid);
        verify(tokenRepository).saveWaitingToken(mockUuid.toString(), createdAt);
    }

    @DisplayName("모든 대기 토큰을 조회한다.")
    @Test
    void findAllPendingTokens() {
        // given
        Set<String> mockTokens = Set.of("token1", "token2");
        when(tokenRepository.getWaitingTokens()).thenReturn(mockTokens);

        // when
        Set<String> result = tokenService.findAllPendingTokens();

        // then
        assertThat(result).containsExactlyInAnyOrder("token1", "token2");
    }

    @DisplayName("유효한 토큰이 큐에 있는지 확인한다.")
    @Test
    void checkTokenQueue_validToken() {
        // given
        UUID tokenUuid = UUID.randomUUID();
        String activeKey = "active-token-key";

        when(tokenRepository.getActiveTokenKeyByValue(tokenUuid.toString())).thenReturn(activeKey);
        when(tokenRepository.isActiveToken(activeKey, tokenUuid.toString())).thenReturn(true);

        // when & then (예외 발생 없음)
        tokenService.checkTokenQueue(tokenUuid);
    }

    @DisplayName("토큰이 유효하지 않으면 예외를 발생시킨다.")
    @Test
    void checkTokenQueue_invalidToken() {
        // given
        UUID tokenUuid = UUID.randomUUID();

        when(tokenRepository.getActiveTokenKeyByValue(tokenUuid.toString())).thenReturn("");

        // when & then
        assertThatThrownBy(() -> tokenService.checkTokenQueue(tokenUuid))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(TOKEN_INVALID.name());
    }

    @DisplayName("대기 토큰을 활성 상태로 변경한다.")
    @Test
    void changeTokenStatusActive() {
        // given
        String tokenId = "test-token-id";
        LocalDateTime now = LocalDateTime.now();

        when(tokenRepository.isWaitingToken(tokenId)).thenReturn(true);

        // when
        tokenService.changeTokenStatusActive(tokenId, now);

        // then
        verify(tokenRepository).deleteByWaitingTokenId(tokenId);
        verify(tokenRepository).saveActiveToken(tokenId, now);
    }

    @DisplayName("대기 토큰이 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void changeTokenStatusActive_invalidToken() {
        // given
        String tokenId = "invalid-token-id";

        when(tokenRepository.isWaitingToken(tokenId)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> tokenService.changeTokenStatusActive(tokenId, LocalDateTime.now()))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(TOKEN_INVALID.name());
    }
}