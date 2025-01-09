package kr.hhplus.be.server.api.token.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.common.exception.enums.ErrorCode;
import kr.hhplus.be.server.api.token.domain.dto.TokenDto;
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
import java.util.Optional;
import java.util.UUID;

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
        TokenDto tokenDto = TokenDto.builder()
                .id(1L)
                .userId(123L)
                .tokenStatus(TokenStatus.PENDING)
                .build();

        Token tokenEntity = tokenDto.toEntity();

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
        TokenDto tokenDto = TokenDto.builder()
                .id(tokenId)
                .userId(123L)
                .tokenStatus(TokenStatus.PENDING)
                .build();

        when(tokenUUIDManager.getTokenIdByTokenUuid(tokenUUID)).thenReturn(tokenId);
        when(tokenRepository.findByTokenId(tokenId)).thenReturn(Optional.of(tokenDto));

        // when
        TokenDto foundToken = tokenService.getTokenInfoByUUID(tokenUUID);

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
                .hasMessageContaining(ErrorCode.TOKEN_INVALID.getMessage());
        verify(tokenUUIDManager, times(1)).getTokenIdByTokenUuid(invalidUUID);
        verify(tokenRepository, times(1)).findByTokenId(invalidTokenId);
    }

}