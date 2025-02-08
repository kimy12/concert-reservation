package kr.hhplus.be.server.api.token.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.token.domain.model.TokenModel;
import kr.hhplus.be.server.api.token.domain.repository.TokenRepository;
import kr.hhplus.be.server.api.token.util.TokenUUIDManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static kr.hhplus.be.server.api.token.domain.enums.TokenErrorCode.TOKEN_INVALID;
import static kr.hhplus.be.server.api.token.domain.enums.TokenErrorCode.TOKEN_PENDING;
import static kr.hhplus.be.server.api.token.domain.enums.TokenStatus.ACTIVE;
import static kr.hhplus.be.server.api.token.domain.enums.TokenStatus.PENDING;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenUUIDManager tokenUUIDManager;

    public UUID saveTokenInfo (long userId, LocalDateTime createdAt) {
        UUID tokenUuid = tokenUUIDManager.createUuidWithLong(userId);
        tokenRepository.saveWaitingToken(tokenUuid.toString(),createdAt);
        return tokenUuid;
    }

    public Set<String> findAllPendingTokens() {
        return tokenRepository.getWaitingTokens();
    }

    public void checkTokenQueue (UUID tokenUUID) {
        String tokenKeyName = tokenRepository.getActiveTokenKeyByValue(tokenUUID.toString());
        if(tokenKeyName.isEmpty()) throw new CustomException(TOKEN_INVALID);
        if(!tokenRepository.isActiveToken(tokenKeyName, tokenUUID.toString())) {
            throw new CustomException(TOKEN_PENDING);
        }
    }

    public void changeTokenStatusActive (String tokenId, LocalDateTime now) {
        if(!tokenRepository.isWaitingToken(tokenId)){
            throw new CustomException(TOKEN_INVALID);
        }
        tokenRepository.deleteByWaitingTokenId(tokenId);
        tokenRepository.saveActiveToken(tokenId, now);
    }
}
