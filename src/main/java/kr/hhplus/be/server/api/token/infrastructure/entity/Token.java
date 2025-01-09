package kr.hhplus.be.server.api.token.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.api.token.domain.dto.TokenDto;
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

    private TokenStatus tokenStatus;

    private LocalDateTime createdAt;

    @Builder
    public Token(long id, long userId, TokenStatus tokenStatus, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.tokenStatus = tokenStatus;
        this.createdAt = createdAt;
    }

    public TokenDto toDto() {
        return TokenDto.builder()
                .id(this.id)
                .userId(this.userId)
                .tokenStatus(this.tokenStatus)
                .createdAt(this.createdAt)
                .build();
    }
}
