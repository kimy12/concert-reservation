package kr.hhplus.be.server.api.point.domain.repository;

import kr.hhplus.be.server.api.point.domain.entity.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserPointRepositoryTest {

    @Autowired
    private UserPointRepository userPointRepository;

    @DisplayName("유저 아이디로 총 포인트를 가져온다.")
    @Test
    void getUserPointByUserId() {
        // given
        long userId = 1L;

        UserPoint userPoint = UserPoint.builder()
                .userId(userId)
                .totalPoint(5000L)
                .build();

        userPointRepository.save(userPoint);

        // when
        Optional<UserPoint> byUserId = userPointRepository.findByUserId(userId);

        // then
        assertThat(byUserId.isPresent()).isTrue();
        assertThat(byUserId.get().getUserId()).isEqualTo(userId);
    }

}