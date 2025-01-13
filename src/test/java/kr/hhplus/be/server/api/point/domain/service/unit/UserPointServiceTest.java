package kr.hhplus.be.server.api.point.domain.service.unit;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.point.domain.dto.PointHistory;
import kr.hhplus.be.server.api.point.domain.dto.UserPoint;
import kr.hhplus.be.server.api.point.domain.repository.UserPointRepository;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.CHARGE;
import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.DEDUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPointServiceTest {
        @Mock
        private UserPointRepository userPointRepository;

        @InjectMocks
        private UserPointService userPointService;

        @DisplayName("유저 아이디로 포인트를 조회한다.")
        @Test
        void getUserPoint() {
            // given
            long userId = 1L;
            UserPoint userPoint = UserPoint.builder()
                    .userId(userId)
                    .totalPoint(5000).build();

            when(userPointRepository.findByUserId(userId))
                    .thenReturn(Optional.of(userPoint));

            // when
            UserPoint result = userPointService.getUserPoint(userId);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getUserId()).isEqualTo(userId);
            assertThat(result.getTotalPoint()).isEqualTo(5000);
        }

        @DisplayName("저장된 포인트가 없는경우 기본값(0)을 가져온다.")
        @Test
        void get0WithoutUserPoint() {
            // given
            long userId = 1L;
            when(userPointRepository.findByUserId(userId))
                    .thenReturn(Optional.empty());

            // when
            UserPoint result = userPointService.getUserPoint(userId);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getUserId()).isEqualTo(userId);
            assertThat(result.getTotalPoint()).isEqualTo(0);
        }

        @DisplayName("포인트를 충전한다.")
        @Test
        void chargePoint() {
            // given
            long userId = 1L;
            long amount = 5000L;
            UserPoint userPoint = UserPoint.builder()
                    .userId(userId)
                    .totalPoint(4000).build();
            when(userPointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

            // when
            UserPoint result = userPointService.chargeOrDeductPoint(userId, amount, CHARGE);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getUserId()).isEqualTo(userId);
            assertThat(result.getTotalPoint()).isEqualTo(9000);
        }

        @DisplayName("포인트 충전 후 포인트 히스토리에 해당내용이 저장된다.")
        @Test
        void pointHistoryAfterChargePoint() {
            // given
            long userId = 1L;
            long amount = 5000L;
            UserPoint userPoint = UserPoint.builder()
                    .userId(userId)
                    .totalPoint(4000).build();
            when(userPointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

            // when
            userPointService.chargeOrDeductPoint(userId, amount, CHARGE);

            // then
            PointHistory addedHistory = userPoint.getPointHistory().get(0);
            assertThat(addedHistory.getAmount()).isEqualTo(5000L);
            assertThat(addedHistory.getType()).isEqualTo(CHARGE);
            assertThat(addedHistory.getUserPoint()).isEqualTo(userPoint);
        }

    @DisplayName("포인트 차감 후 포인트 히스토리에 해당내용이 저장된다.")
    @Test
    void pointHistoryAfterDeductPoint() {
        // given
        long userId = 1L;
        long amount = 5000L;
        UserPoint userPoint = UserPoint.builder()
                .userId(userId)
                .totalPoint(7000).build();
        when(userPointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

        // when
        userPointService.chargeOrDeductPoint(userId, amount, DEDUCT);

        // then
        PointHistory addedHistory = userPoint.getPointHistory().get(0);
        assertThat(addedHistory.getAmount()).isEqualTo(5000L);
        assertThat(addedHistory.getType()).isEqualTo(DEDUCT);
        assertThat(addedHistory.getUserPoint()).isEqualTo(userPoint);
    }

    @DisplayName("포인트가 없을때 사용요청시 예외가 발생한다.")
    @Test
    void deductPointWithNoPoint() {
        // given
        long userId = 1L;
        long amount = 5000L;
        UserPoint userPoint = UserPoint.builder()
                .userId(userId)
                .totalPoint(4900L).build();
        when(userPointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

        // when // then
        assertThatThrownBy(()-> userPointService.chargeOrDeductPoint(userId, amount, DEDUCT))
                .isInstanceOf(CustomException.class)
                .hasMessage("포인트가 부족합니다.");
    }
}