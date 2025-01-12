package kr.hhplus.be.server.api.point.application;

import kr.hhplus.be.server.api.point.domain.dto.User;
import kr.hhplus.be.server.api.point.domain.dto.UserPoint;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import kr.hhplus.be.server.api.point.domain.service.UserService;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPointFacade {
    private final UserPointService userPointService;
    public final UserService userService;

    public PointResponse.TotalPoint getUserPoint(long userId) {
        User user = userService.findById(userId);
        UserPoint userPoint = userPointService.getUserPoint(user.getId());

        return PointResponse.TotalPoint.builder()
                .userId(userPoint.getUserId())
                .totalPoint(userPoint.getTotalPoint())
                .build();
    }

    public PointResponse.TotalPoint chargePoint(PointRequest.ChargePoint pointRequest) {
        User user = userService.findById(pointRequest.userId());
        UserPoint userPoint = userPointService.chargePoint(user.getId(), pointRequest.amount());

        return PointResponse.TotalPoint.builder()
                .userId(userPoint.getUserId())
                .totalPoint(userPoint.getTotalPoint())
                .build();
    }

    public PointResponse.TotalPoint deductPoint(PointRequest.DeductPoint pointRequest) {
        User user = userService.findById(pointRequest.userId());
        UserPoint userPoint = userPointService.deductPoint(user.getId(), pointRequest.amount());

        return PointResponse.TotalPoint.builder()
                .userId(userPoint.getUserId())
                .totalPoint(userPoint.getTotalPoint())
                .build();
    }
}
