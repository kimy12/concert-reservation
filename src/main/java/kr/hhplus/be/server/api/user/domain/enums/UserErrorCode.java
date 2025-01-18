package kr.hhplus.be.server.api.user.domain.enums;

import kr.hhplus.be.server.api.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NO_CONTENT,"USER-01", "유저가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    UserErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
