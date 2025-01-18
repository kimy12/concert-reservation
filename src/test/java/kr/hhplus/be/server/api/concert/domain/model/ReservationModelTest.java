package kr.hhplus.be.server.api.concert.domain.model;

import kr.hhplus.be.server.api.common.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservationModelTest {

    @DisplayName("선점한 자리가 5분이 지나면 예외가 발생한다.")
    @Test
    void checkCreatedAt() {
        // given
        LocalDateTime createdAt = LocalDateTime.of(2025,1,17,2,0);
        LocalDateTime now = createdAt.plusMinutes(10);
        ReservationModel reservedId = ReservationModel.builder()
                .id(1L)
                .userId(1L)
                .seatId(1L)
                .createdAt(createdAt)
                .build();

        // when // then
        assertThatThrownBy(()->reservedId.checkCreatedAt(now))
                .isInstanceOf(CustomException.class)
                .hasMessage("예약 시간이 초과되었습니다.");
    }

}