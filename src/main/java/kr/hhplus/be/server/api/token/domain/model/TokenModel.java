package kr.hhplus.be.server.api.token.domain.model;

import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TokenModel {

    private long id;

    private long userId;

    private TokenStatus tokenStatus;

    private LocalDateTime createdAt;

    @Builder
    public TokenModel(long id, long userId, TokenStatus tokenStatus, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.tokenStatus = tokenStatus;
        this.createdAt = createdAt;
    }

    public Token toEntity() {
        return Token.builder()
                .id(this.id)
                .tokenStatus(this.tokenStatus)
                .createdAt(this.createdAt)
                .userId(this.userId)
                .build();
    }

    public void turnToActive (){
        this.tokenStatus = TokenStatus.ACTIVE;
    }

    public boolean checkCreatedAt (LocalDateTime now) {
        LocalDateTime expiredAt = this.createdAt.plusMinutes(10);
        return expiredAt.isBefore(now);
    }
}
