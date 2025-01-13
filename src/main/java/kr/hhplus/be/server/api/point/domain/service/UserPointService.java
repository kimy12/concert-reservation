package kr.hhplus.be.server.api.point.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.point.domain.dto.PointHistory;
import kr.hhplus.be.server.api.point.domain.dto.UserPoint;
import kr.hhplus.be.server.api.point.domain.enums.PointHistoryType;
import kr.hhplus.be.server.api.point.domain.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UserPoint chargeOrDeductPoint (long userId, long amount, PointHistoryType pointHistoryType) {
        UserPoint userPoint = userPointRepository.findByUserId(userId)
                                                    .orElseGet(() -> UserPoint.builder()
                                                            .userId(userId)
                                                            .build());

        if(pointHistoryType.equals(CHARGE)) {
            userPoint.addPoints(amount);
        } else if(pointHistoryType.equals(DEDUCT)) {
            userPoint.deductPoint(amount);
        }

        PointHistory pointHistory = PointHistory.builder()
                .userPoint(userPoint)
                .amount(amount)
                .type(pointHistoryType)
                .build();

        userPoint.addPointHistory(pointHistory);

        return userPoint;
    }

//    @Transactional
//    public UserPoint deductPoint (long userId, long amount) {
//        UserPoint userPoint = userPointRepository.findByUserId(userId)
//                                                    .orElseThrow(()->new CustomException(POINT_NOT_FOUND));
//
//        userPoint.deductPoint(amount);
//
//        PointHistory pointHistory = PointHistory.builder()
//                .userPoint(userPoint)
//                .amount(amount)
//                .type(DEDUCT)
//                .build();
//
//        userPoint.addPointHistory(pointHistory);
//
//        return userPoint;
//    }

}
