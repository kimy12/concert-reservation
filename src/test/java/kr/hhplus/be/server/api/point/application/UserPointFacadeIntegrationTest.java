package kr.hhplus.be.server.api.point.application;

import kr.hhplus.be.server.api.point.domain.entity.UserPoint;
import kr.hhplus.be.server.api.point.domain.enums.PointHistoryType;
import kr.hhplus.be.server.api.point.domain.repository.UserPointRepository;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.user.domain.entity.User;
import kr.hhplus.be.server.api.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserPointFacadeIntegrationTest {

    @Autowired
    private UserPointFacade userPointFacade;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("같은 아이디가 5번 요청 하는경우, 한번만 충전 성공한다.")
    @Test
    void payReservedSeat() throws InterruptedException{
        // given
        int memberCount = 10;

        // 사용자 및 초기 포인트 설정
        User save = userRepository.save(User.builder().build());
        PointRequest.ChargePoint request = new PointRequest.ChargePoint(save.getId(), 5000L, PointHistoryType.CHARGE);

        UserPoint userPoint = UserPoint.builder()
                .userId(save.getId())
                .totalPoint(20000L)
                .build();
        userPointRepository.save(userPoint);

        // 성공 및 실패 카운터
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // 동시 실행 준비
        ExecutorService executorService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch latch = new CountDownLatch(memberCount);

        // when
        for (int i = 0; i < memberCount; i++) {
            executorService.submit(() -> {
                try {
                    userPointFacade.chargePoint(request);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(); // 모든 작업 완료 대기
        executorService.shutdown();

        // then
        UserPoint updatedUserPoint = userPointRepository.findByUserId(save.getId())
                .orElseThrow();

        // 포인트가 한 번만 충전되었는지 확인
        assertThat(updatedUserPoint.getTotalPoint()).isEqualTo(70000L);
    }
}
