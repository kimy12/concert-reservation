package kr.hhplus.be.server.api.concert.domain.enums.error;

import kr.hhplus.be.server.api.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SeatErrorCode implements ErrorCode {

    SEAT_NOT_FOUND(HttpStatus.NO_CONTENT,"SEAT-01", "유효하지 않는 좌석입니다."),
    SEAT_NOT_AVAILABLE(HttpStatus.CONFLICT, "SEAT-02", "이미 예약 완료된 좌석입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    SeatErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
