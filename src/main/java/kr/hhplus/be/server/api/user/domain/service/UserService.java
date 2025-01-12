package kr.hhplus.be.server.api.user.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.common.exception.enums.ErrorCode;
import kr.hhplus.be.server.api.user.domain.dto.User;
import kr.hhplus.be.server.api.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public User findById (long id) {
        return userRepository.findById(id).orElseThrow(
                ()->new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
