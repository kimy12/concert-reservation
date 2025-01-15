package kr.hhplus.be.server.api.token.domain.repository;

import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import kr.hhplus.be.server.api.token.domain.model.TokenModel;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    Optional<TokenModel> findByTokenId(long token);

    TokenModel save(Token token);

    List<TokenModel> findAllByTokenStatusOrderByIdAsc (TokenStatus tokenStatus);

    List<TokenModel> findAllByTokenStatus(TokenStatus tokenStatus);

    void deleteByTokenId(long token);

}
