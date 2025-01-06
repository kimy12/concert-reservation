package kr.hhplus.be.server.api.point.presentation.dto;

import kr.hhplus.be.server.api.point.domain.enums.PointHistoryType;
import lombok.Builder;

public class PointRequest {
    @Builder
    public record ChargePoint(
            long userId,
            long amount,
            PointHistoryType pointHistoryType
    ) {
    }

    @Builder
    public record DeductPoint(
            long userId,
            long amount,
            PointHistoryType pointHistoryType
    ) {
    }
}
