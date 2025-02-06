package kr.hhplus.be.server.api.token.domain.repository;

import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import kr.hhplus.be.server.api.token.domain.model.TokenModel;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TokenRepository {

    void saveWaitingToken(String userId, LocalDateTime time);

    boolean isWaitingToken(String token);

    boolean isActiveToken(String activeTokenKetName, String token);

    void deleteByWaitingTokenId(String token);

    void saveActiveToken(String userId, LocalDateTime time);

    Set<String> getWaitingTokens();

    String getActiveTokenKeyByValue(String value);

}
