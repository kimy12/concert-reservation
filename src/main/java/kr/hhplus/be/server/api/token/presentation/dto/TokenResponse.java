package kr.hhplus.be.server.api.token.presentation.dto;

import lombok.Builder;

import java.util.UUID;

public class TokenResponse {

    @Builder
    public record Response(
            String token,
            String status)
    {}
}
