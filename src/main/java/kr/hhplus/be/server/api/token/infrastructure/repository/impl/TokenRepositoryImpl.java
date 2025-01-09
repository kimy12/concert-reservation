package kr.hhplus.be.server.api.token.infrastructure.repository.impl;

import kr.hhplus.be.server.api.token.domain.dto.TokenDto;
import kr.hhplus.be.server.api.token.domain.repository.TokenRepository;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;
import kr.hhplus.be.server.api.token.infrastructure.repository.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public Optional<TokenDto> findByTokenId(long uuid) {
        return tokenJpaRepository.findById(uuid)
                .map(Token :: toDto);
    }

    @Override
    public TokenDto save(Token token) {
        return tokenJpaRepository.save(token).toDto();
    }

    @Override
    public int deleteExpiredToken(LocalDateTime thisTime) {
        return tokenJpaRepository.deleteTokensOlderThan(thisTime);
    }
}
