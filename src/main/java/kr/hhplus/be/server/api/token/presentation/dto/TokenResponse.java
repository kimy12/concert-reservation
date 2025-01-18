package kr.hhplus.be.server.api.token.presentation.dto;

import kr.hhplus.be.server.api.token.domain.enums.TokenStatus;
import lombok.Builder;

import java.util.UUID;

public class TokenResponse {

    @Builder
    public record Response(
            String token,
            TokenStatus status)
    {}
}
