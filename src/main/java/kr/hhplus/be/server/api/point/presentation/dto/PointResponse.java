package kr.hhplus.be.server.api.point.presentation.dto;

import lombok.Builder;

public class PointResponse {
    @Builder
    public record TotalPoint(
            long userId,
            long totalPoint
    ) {
    }
}
