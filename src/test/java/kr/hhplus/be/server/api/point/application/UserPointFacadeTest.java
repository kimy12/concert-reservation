package kr.hhplus.be.server.api.point.application;

import kr.hhplus.be.server.api.point.domain.dto.User;
import kr.hhplus.be.server.api.point.domain.dto.UserPoint;
import kr.hhplus.be.server.api.point.domain.enums.PointHistoryType;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import kr.hhplus.be.server.api.point.domain.service.UserService;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserPointFacadeTest {

    @InjectMocks
    private UserPointFacade userPointFacade;

    @Mock
    private UserPointService userPointService;

    @Mock
    private UserService userService;

    @DisplayName("사용자의 포인트를 조회한다.")
    @Test
    void getUserPoint() {
        // given
        long userId = 1L;
        User mockUser = User.builder().id(userId).build();
        UserPoint mockUserPoint = UserPoint.builder().userId(userId).totalPoint(5000).build();

        when(userService.findById(userId)).thenReturn(mockUser);
        when(userPointService.getUserPoint(userId)).thenReturn(mockUserPoint);

        // when
        PointResponse.TotalPoint result = userPointFacade.getUserPoint(userId);

        // then
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.totalPoint()).isEqualTo(5000);
        verify(userService, times(1)).findById(userId);
        verify(userPointService, times(1)).getUserPoint(userId);
    }

    @DisplayName("사용자의 포인트를 충전한다.")
    @Test
    void chargePoint() {
        // given
        long userId = 1L;
        long amount = 1000L;

        User mockUser = User.builder().id(userId).build();
        UserPoint mockUserPoint = UserPoint.builder().userId(userId).totalPoint(6000).build();

        PointRequest.ChargePoint pointRequest = new PointRequest.ChargePoint(userId, amount, PointHistoryType.CHARGE);

        when(userService.findById(userId)).thenReturn(mockUser);
        when(userPointService.chargePoint(userId, amount)).thenReturn(mockUserPoint);

        // when
        PointResponse.TotalPoint result = userPointFacade.chargePoint(pointRequest);

        // then
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.totalPoint()).isEqualTo(6000);
        verify(userService, times(1)).findById(userId);
        verify(userPointService, times(1)).chargePoint(userId, amount);
    }

    @DisplayName("사용자의 포인트를 차감한다.")
    @Test
    void deductPoint() {
        // given
        long userId = 1L;
        long amount = 1000L;

        User mockUser = User.builder().id(userId).build();
        UserPoint mockUserPoint = UserPoint.builder().userId(userId).totalPoint(4000).build();

        PointRequest.DeductPoint pointRequest = new PointRequest.DeductPoint(userId, amount, PointHistoryType.DEDUCT);

        when(userService.findById(userId)).thenReturn(mockUser);
        when(userPointService.deductPoint(userId, amount)).thenReturn(mockUserPoint);

        // when
        PointResponse.TotalPoint result = userPointFacade.deductPoint(pointRequest);

        // then
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.totalPoint()).isEqualTo(4000);
        verify(userService, times(1)).findById(userId);
        verify(userPointService, times(1)).deductPoint(userId, amount);
    }

}