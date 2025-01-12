package kr.hhplus.be.server.api.token.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.common.exception.enums.ErrorCode;
import kr.hhplus.be.server.api.token.domain.dto.TokenDto;
import kr.hhplus.be.server.api.token.domain.repository.TokenRepository;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;
import kr.hhplus.be.server.api.token.util.TokenUUIDManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kr.hhplus.be.server.api.token.domain.enums.TokenStatus.PENDING;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenUUIDManager tokenUUIDManager;

    @Transactional
    public UUID saveTokenInfo (long userId) {
        TokenDto token = TokenDto.builder()
                .userId(userId)
                .tokenStatus(PENDING)
                .build();
        TokenDto savedTokenDto = tokenRepository.save(token.toEntity());
        return tokenUUIDManager.createUuidWithLong(savedTokenDto.getId());
    }

    public TokenDto getTokenInfoByUUID(UUID tokenUUID) {
        long tokenId = tokenUUIDManager.getTokenIdByTokenUuid(tokenUUID);
        return tokenRepository.findByTokenId(tokenId)
                .orElseThrow(()->new CustomException(ErrorCode.TOKEN_INVALID));
    }
    

}
