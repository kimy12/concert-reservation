package kr.hhplus.be.server.api.token.infrastructure.repository.impl;

import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import kr.hhplus.be.server.api.token.domain.model.TokenModel;
import kr.hhplus.be.server.api.token.domain.repository.TokenRepository;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;
import kr.hhplus.be.server.api.token.infrastructure.repository.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public Optional<TokenModel> findByTokenId(long uuid) {
        return tokenJpaRepository.findById(uuid)
                .map(Token :: toDto);
    }

    @Override
    public TokenModel save(Token token) {
        return tokenJpaRepository.save(token).toDto();
    }

    @Override
    public List<TokenModel> findAllByTokenStatusOrderByIdAsc(TokenStatus tokenStatus) {
        return tokenJpaRepository.findAllByTokenStatusOrderByIdAsc(tokenStatus)
                .stream()
                .map(Token :: toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TokenModel> findAllByTokenStatus(TokenStatus tokenStatus) {
        return tokenJpaRepository.findAllByTokenStatus(tokenStatus)
                .stream()
                .map(Token :: toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByTokenId(long token) {
        tokenJpaRepository.deleteById(token);
    }
}
