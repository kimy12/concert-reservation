package kr.hhplus.be.server.api.point.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.point.domain.entity.PointHistory;
import kr.hhplus.be.server.api.point.domain.entity.UserPoint;
import kr.hhplus.be.server.api.point.domain.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.POINT_NOT_FOUND;
import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.CHARGE;
import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.DEDUCT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPointService {
    private final UserPointRepository userPointRepository;

    public UserPoint getUserPoint (Long userId) {
        return userPointRepository.findByUserId(userId)
                                        .orElseGet(() -> UserPoint.builder()
                                                            .userId(userId)
                                                            .totalPoint(0)
                                                            .build());
    }

    @Transactional
    public UserPoint chargePoint (long userId, long amount) {
        UserPoint userPoint = userPointRepository.findByUserId(userId)
                                                    .orElseGet(() -> UserPoint.builder()
                                                            .userId(userId)
                                                            .build());

        userPoint.addPoints(amount);

        PointHistory pointHistory = PointHistory.builder()
                .userPoint(userPoint)
                .amount(amount)
                .type(CHARGE)
                .build();

        userPoint.addPointHistory(pointHistory);

        return userPoint;
    }

    @Transactional
    public UserPoint deductPoint (long userId, long amount) {
        UserPoint userPoint = userPointRepository.findByUserId(userId)
                                                    .orElseThrow(()->new CustomException(POINT_NOT_FOUND));

        userPoint.deductPoint(amount);

        PointHistory pointHistory = PointHistory.builder()
                .userPoint(userPoint)
                .amount(amount)
                .type(DEDUCT)
                .build();

        userPoint.addPointHistory(pointHistory);

        return userPoint;
    }

}
