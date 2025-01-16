package kr.hhplus.be.server.api.point.application;

import kr.hhplus.be.server.api.concert.domain.service.ReservationService;
import kr.hhplus.be.server.api.user.domain.entity.User;
import kr.hhplus.be.server.api.point.domain.entity.UserPoint;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import kr.hhplus.be.server.api.user.domain.service.UserService;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.CHARGE;
import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.DEDUCT;

@Component
@RequiredArgsConstructor
public class UserPointFacade {
    private final UserPointService userPointService;
    public final UserService userService;
    private final ReservationService reservationService;

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
        UserPoint userPoint = userPointService.chargeOrDeductPoint(user.getId(), pointRequest.amount(),CHARGE);

        return PointResponse.TotalPoint.builder()
                .userId(userPoint.getUserId())
                .totalPoint(userPoint.getTotalPoint())
                .build();
    }

    public PointResponse.TotalPoint deductPoint(PointRequest.DeductPoint pointRequest) {
        User user = userService.findById(pointRequest.userId());
        UserPoint userPoint = userPointService.chargeOrDeductPoint(user.getId(), pointRequest.amount(), DEDUCT);

        return PointResponse.TotalPoint.builder()
                .userId(userPoint.getUserId())
                .totalPoint(userPoint.getTotalPoint())
                .build();
    }
}
