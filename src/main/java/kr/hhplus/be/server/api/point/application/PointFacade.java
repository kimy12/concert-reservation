package kr.hhplus.be.server.api.point.application;

import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointFacade {
    public PointResponse getUserPoint(long userId) {
        return null;
    }

    public PointResponse chargePoint(PointRequest.ChargePoint pointRequest) {
        return null;
    }
}
