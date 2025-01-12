package kr.hhplus.be.server.api.token.domain.repository;

import kr.hhplus.be.server.api.token.domain.dto.TokenDto;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    Optional<TokenDto> findByTokenId(long token);

    TokenDto save(Token token);

    int deleteExpiredToken (LocalDateTime thisTime);


}
