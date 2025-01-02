package kr.hhplus.be.server.api.token.presentation.dto;

import lombok.Builder;

public class TokenRequest {
    @Builder
    public record Request(
            long userId
    ){}
}
