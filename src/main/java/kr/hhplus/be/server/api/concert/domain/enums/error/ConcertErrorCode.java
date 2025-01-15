package kr.hhplus.be.server.api.concert.domain.enums.error;

import kr.hhplus.be.server.api.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ConcertErrorCode implements ErrorCode {

    CONCERT_NOT_FOUND(HttpStatus.NO_CONTENT,"CONCERT-01", "콘서트가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ConcertErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
