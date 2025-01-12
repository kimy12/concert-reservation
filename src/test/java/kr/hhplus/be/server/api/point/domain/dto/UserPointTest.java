package kr.hhplus.be.server.api.point.domain.dto;

import kr.hhplus.be.server.api.common.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserPointTest {

    @DisplayName("포인트를 더한다.")
    @Test
    void addPoint() {
        // given
        long point = 5000L;
        long amount = 500L;
        UserPoint userPoint = new UserPoint(1,2,point);

        // when
        long totalPoint = userPoint.addPoints(amount);

        // then
        assertEquals(5500, totalPoint);
    }

    @DisplayName("포인트를 차감한다.")
    @Test
    void deductPoint() {
        // given
        long point = 5000L;
        long amount = 500L;
        UserPoint userPoint = new UserPoint(1,2,point);

        // when
        long totalPoint = userPoint.deductPoint(amount);

        // then
        assertEquals(4500, totalPoint);
    }

    @DisplayName("존재하는 포인트보다 차감하려는 포인트가 크면 예외가 발생한다.")
    @Test
    void checkPointBeforeDeducting() {
        // given
        long point = 5000L;
        long amount = 5001L;
        UserPoint userPoint = new UserPoint(1,2,point);

        //when // then
        assertThatThrownBy(()->userPoint.checkPointBeforeDeducting(amount))
                .isInstanceOf(CustomException.class)
                .hasMessage("포인트가 부족합니다.");
    }
}