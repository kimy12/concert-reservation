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

    @DisplayName("uuid를 생성한다.")
    @Test
    void createUUID() {
        // given
        TokenModel tokenModel = TokenModel.builder()
                .id(1L)
                .userId(123L)
                .tokenStatus(TokenStatus.PENDING)
                .build();

        Token tokenEntity = tokenModel.toEntity();

        when(tokenRepository.save(any(Token.class))).thenReturn(tokenEntity.toDto());
        when(tokenUUIDManager.createUuidWithLong(1L)).thenReturn(UUID.randomUUID());

        // when
        UUID tokenUUID = tokenService.saveTokenInfo(123L);

        // then
        assertThat(tokenUUID).isNotNull();
        verify(tokenRepository, times(1)).save(any());
        verify(tokenUUIDManager, times(1)).createUuidWithLong(1L);
    }

    @DisplayName("토큰 UUID로 토큰 정보를 조회한다.")
    @Test
    void getTokenInfoByUUID() {
        // given
        UUID tokenUUID = UUID.randomUUID();
        long tokenId = 1L;
        TokenModel tokenModel = TokenModel.builder()
                .id(tokenId)
                .userId(123L)
                .tokenStatus(TokenStatus.PENDING)
                .build();

        when(tokenUUIDManager.getTokenIdByTokenUuid(tokenUUID)).thenReturn(tokenId);
        when(tokenRepository.findByTokenId(tokenId)).thenReturn(Optional.of(tokenModel));

        // when
        TokenModel foundToken = tokenService.getTokenInfoByUUID(tokenUUID);

        // then
        assertThat(foundToken).isNotNull();
        assertThat(foundToken.getId()).isEqualTo(tokenId);
        verify(tokenUUIDManager, times(1)).getTokenIdByTokenUuid(tokenUUID);
        verify(tokenRepository, times(1)).findByTokenId(tokenId);
    }

    @DisplayName("유효하지 않은 UUID로 토큰 정보를 조회할 때 예외를 발생시킨다.")
    @Test
    void getTokenInfoByInvalidUUID() {
        // given
        UUID invalidUUID = UUID.randomUUID();
        long invalidTokenId = 99L;

        when(tokenUUIDManager.getTokenIdByTokenUuid(invalidUUID)).thenReturn(invalidTokenId);
        when(tokenRepository.findByTokenId(invalidTokenId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> tokenService.getTokenInfoByUUID(invalidUUID))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(TOKEN_INVALID.getMessage());
        verify(tokenUUIDManager, times(1)).getTokenIdByTokenUuid(invalidUUID);
        verify(tokenRepository, times(1)).findByTokenId(invalidTokenId);
    }

    @DisplayName("pending 상태의 토큰을 필요한 개수 만큼 가져온다.")
    @Test
    void findAllPendingTokens() {
        // given
        TokenModel tokenModel1 = new TokenModel(1, 1, TokenStatus.PENDING, LocalDateTime.of(2025, 1, 2, 1, 1));
        TokenModel tokenModel2 = new TokenModel(2, 2, TokenStatus.PENDING, LocalDateTime.of(2025, 1, 2, 1, 1));
        List<TokenModel> result = List.of(tokenModel1, tokenModel2);
        TokenModel tokenModel3 = new TokenModel(3, 3, TokenStatus.ACTIVE, LocalDateTime.of(2025, 1, 2, 1, 1));
        TokenModel tokenModel4 = new TokenModel(4, 4, TokenStatus.ACTIVE, LocalDateTime.of(2025, 1, 2, 1, 1));
        List<TokenModel> result2 = List.of(tokenModel3, tokenModel4);
        when(tokenRepository.findAllByTokenStatus(TokenStatus.ACTIVE)).thenReturn(result2);
        when(tokenRepository.findAllByTokenStatusOrderByIdAsc(TokenStatus.PENDING)).thenReturn(result);

        // when
        List<TokenModel> allPendingTokens = tokenService.findAllPendingTokens(3);

        // then
        assertThat(allPendingTokens).hasSize(1);
        assertThat(allPendingTokens.get(0)).isEqualTo(tokenModel1);

    }

    @DisplayName("토큰 상태코드를 activate로 변경한다.")
    @Test
    void changeTokenStatusActive() {
        // given
        long tokenId = 1L;
        TokenModel tokenModel = new TokenModel(tokenId, 1, TokenStatus.PENDING, LocalDateTime.of(2025, 1, 2, 1, 1));
        when(tokenRepository.findByTokenId(tokenId)).thenReturn(Optional.of(tokenModel));

        // when
        tokenService.changeTokenStatusActive(tokenId);

        // then
        assertThat(tokenModel.getTokenStatus()).isEqualTo(TokenStatus.ACTIVE);
    }

    @DisplayName("유효 하지 않은 토큰의 토큰상태를 수정하려고 하면 예외가 발생한다.")
    @Test
    void changeTokenStatusActiveWithoutTokenInfo() {
        // given
        long tokenId = 1L;
        when(tokenRepository.findByTokenId(tokenId)).thenReturn(Optional.empty());

        //when // then
        assertThatThrownBy(() -> tokenService.changeTokenStatusActive(tokenId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(TOKEN_INVALID.getMessage());
    }
}