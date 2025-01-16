package kr.hhplus.be.server.api.point.domain.service.unit;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.user.domain.entity.User;
import kr.hhplus.be.server.api.user.domain.repository.UserRepository;
import kr.hhplus.be.server.api.user.domain.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @DisplayName("유저아이디를 조회한다.")
    @Test
    void getUserId() {
        // given
        long userId = 1L;

        User user = User.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User result = userService.findById(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
    }

    @DisplayName("존재하지 않는 유저를 조회할 경우 예외가 발생한다.")
    @Test
    void getUserNoExist() {
        // given
        long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(()->userService.findById(userId))
                .isInstanceOf(CustomException.class)
                .hasMessage("유저가 존재하지 않습니다.");
    }


}