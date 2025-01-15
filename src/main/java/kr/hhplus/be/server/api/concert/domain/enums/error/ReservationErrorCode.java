package kr.hhplus.be.server.api.concert.domain.enums.error;

import kr.hhplus.be.server.api.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReservationErrorCode implements ErrorCode {

    RESERVATION_TIME_INVALID(HttpStatus.BAD_REQUEST,"RESERVATION-01", "예약 기간이 아닙니다."),
    RESERVATION_DATE_NOT_FOUND(HttpStatus.NO_CONTENT,"RESERVATION-02", "예약 가능한 날짜가 없습니다."),
    RESERVATION_SEAT_NOT_FOUND(HttpStatus.NO_CONTENT,"RESERVATION-03", "예약 가능한 좌석이 없습니다."),
    RESERVATION_TIME_EXCEEDED(HttpStatus.BAD_REQUEST, "RESERVATION-04", "예약 시간이 초과되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ReservationErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
