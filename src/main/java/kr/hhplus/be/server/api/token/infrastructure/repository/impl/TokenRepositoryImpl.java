package kr.hhplus.be.server.api.token.infrastructure.repository.impl;

import kr.hhplus.be.server.api.token.domain.repository.TokenRepository;
import kr.hhplus.be.server.api.token.infrastructure.repository.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenRedisRepository tokenRedisRepository;


    @Override
    public void saveWaitingToken(String userId, LocalDateTime time) {
        tokenRedisRepository.saveWaitingToken(userId, time);
    }

    @Override
    public boolean isWaitingToken(String token) {
        return tokenRedisRepository.isWaitingTokenExists(token);
    }

    @Override
    public boolean isActiveToken(String activeTokenKetName, String token) {
        return tokenRedisRepository.isActiveTokenExists(activeTokenKetName, token);
    }

    @Override
    public void deleteByWaitingTokenId(String token) {
        tokenRedisRepository.removeWaitingToken(token);
    }

    @Override
    public void saveActiveToken(String userId, LocalDateTime time) {
        tokenRedisRepository.saveActiveToken(userId, time);
    }

    @Override
    public Set<String> getWaitingTokens() {
        return tokenRedisRepository.getAllWaitingKeysOrdered();
    }

    @Override
    public String getActiveTokenKeyByValue(String value) {
        return tokenRedisRepository.getActiveTokenKeyByValue(value);
    }
}
