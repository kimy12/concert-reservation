package kr.hhplus.be.server.api.point.presentation.dto;

public class PointRequest {
    public record ChargePoint(
            long userId,
            long amount
    ) {
    }
}
