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

    private static final int TOKEN_QUE = 50;

    @Transactional
    public UUID saveTokenInfo (long userId) {
        TokenModel token = TokenModel.builder()
                .userId(userId)
                .tokenStatus(PENDING)
                .build();
        TokenModel savedTokenModel = tokenRepository.save(token.toEntity());
        return tokenUUIDManager.createUuidWithLong(savedTokenModel.getId());
    }

    public TokenModel getTokenInfoByUUID(UUID tokenUUID) {
        long tokenId = tokenUUIDManager.getTokenIdByTokenUuid(tokenUUID);
        return tokenRepository.findByTokenId(tokenId)
                .orElseThrow(()->new CustomException(TOKEN_INVALID));
    }

    public List<TokenModel> findAllPendingTokens(int maxActive) {
        int activeToken = tokenRepository.findAllByTokenStatus(ACTIVE).size();
        int limit = maxActive <= activeToken ? 0 : maxActive-activeToken;
        return tokenRepository.findAllByTokenStatusOrderByIdAsc(PENDING)
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Transactional
    public void checkTokenQueue (UUID tokenUUID, LocalDateTime now) {
        long tokenId = tokenUUIDManager.getTokenIdByTokenUuid(tokenUUID);
        TokenModel tokenModel = tokenRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new CustomException(TOKEN_INVALID));
        if(!ACTIVE.equals(tokenModel.getTokenStatus())) {
            throw new CustomException(TOKEN_PENDING);
        }
        tokenModel.updateTokenStatus(now);
    }

    public List<TokenModel> findAllByTokenStatusActive(){
        return tokenRepository.findAllByTokenStatus(ACTIVE);
    }

    @Transactional
    public void changeTokenStatusActive (long tokenId) {
        TokenModel tokenModel = tokenRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new CustomException(TOKEN_INVALID));
        tokenModel.turnToActive();
    }

    @Transactional
    public void deleteTokenInfo (long tokenId) {
        tokenRepository.deleteByTokenId(tokenId);
    }
}
