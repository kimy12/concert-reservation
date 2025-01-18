package kr.hhplus.be.server.api.token.domain.model;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import kr.hhplus.be.server.api.token.infrastructure.entity.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static kr.hhplus.be.server.api.token.domain.enums.TokenErrorCode.TOKEN_EXPIRED;

@Getter
@Setter
public class TokenModel {

    private long id;

    private long userId;

    private TokenStatus tokenStatus;

    private LocalDateTime expireAt;

    @Builder
    public TokenModel(long id, long userId, TokenStatus tokenStatus, LocalDateTime expireAt) {
        this.id = id;
        this.userId = userId;
        this.tokenStatus = tokenStatus;
        this.expireAt = expireAt;
    }

    public Token toEntity() {
        return Token.builder()
                .id(this.id)
                .tokenStatus(this.tokenStatus)
                .expireAt(this.expireAt)
                .userId(this.userId)
                .build();
    }

    public void turnToActive (){
        this.tokenStatus = TokenStatus.ACTIVE;
    }

    public void updateTokenStatus (LocalDateTime now) {
        if(this.expireAt.isBefore(now)){
            this.tokenStatus = TokenStatus.EXPIRED;
            throw new CustomException(TOKEN_EXPIRED);
        }
    }

    public boolean checkExpireAt (LocalDateTime now) {
        return this.expireAt.isBefore(now);
    }

}
