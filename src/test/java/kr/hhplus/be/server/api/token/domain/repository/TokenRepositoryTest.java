package kr.hhplus.be.server.api.token.domain.repository;

import kr.hhplus.be.server.api.token.domain.model.TokenModel;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;
import kr.hhplus.be.server.api.token.infrastructure.repository.TokenJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static kr.hhplus.be.server.api.token.domain.enums.TokenStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenJpaRepository tokenJpaRepository;

    @DisplayName("토큰 id로 토큰을 찾는다.")
    @Test
    void findByTokenId() {
        // given
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 0, 0);
        Token build = Token.builder()
                .tokenStatus(PENDING)
                .userId(3)
                .expireAt(date)
                .build();


        Token token = tokenJpaRepository.save(build);

        // when
        Optional<TokenModel> byTokenId =
                tokenRepository.findByTokenId(token.getId());

        // then
        assertThat(byTokenId).isPresent();
        assertThat(byTokenId.get().getUserId()).isEqualTo(3);
        assertThat(byTokenId.get().getTokenStatus()).isEqualTo(PENDING);
    }



    @DisplayName("토큰을 저장한다.")
    @Test
    void saveTokenInfo() {
        // given
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 0, 0);
        Token build = Token.builder()
                .tokenStatus(PENDING)
                .userId(3)
                .expireAt(date)
                .build();

        // when
        TokenModel saved = tokenRepository.save(build);

        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getTokenStatus()).isEqualTo(PENDING);
        assertThat(saved.getUserId()).isEqualTo(3);
        assertThat(saved.getExpireAt()).isEqualTo(date);
    }



}