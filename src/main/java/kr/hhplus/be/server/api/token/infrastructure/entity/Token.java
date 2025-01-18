package kr.hhplus.be.server.api.token.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.api.token.domain.model.TokenModel;
import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    private LocalDateTime expireAt;

    @Builder
    public Token(long id, long userId, TokenStatus tokenStatus, LocalDateTime expireAt) {
        this.id = id;
        this.userId = userId;
        this.tokenStatus = tokenStatus;
        this.expireAt = expireAt;
    }

    public TokenModel toDto() {
        return TokenModel.builder()
                .id(this.id)
                .userId(this.userId)
                .tokenStatus(this.tokenStatus)
                .expireAt(this.expireAt)
                .build();
    }

}
