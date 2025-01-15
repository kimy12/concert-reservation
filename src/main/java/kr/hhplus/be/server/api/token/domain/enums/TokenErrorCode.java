package kr.hhplus.be.server.api.token.domain.enums;

import kr.hhplus.be.server.api.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenErrorCode implements ErrorCode {

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-01", "토큰이 만료되었습니다."),
    TOKEN_PENDING(HttpStatus.FORBIDDEN, "AUTH-02", "토큰이 아직 활성화되지 않았습니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "AUTH-03", "정상적인 토큰이 아닙니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    TokenErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
