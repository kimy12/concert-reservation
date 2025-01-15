package kr.hhplus.be.server.api.common.exception.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Schema(description = "Error codes used in the application")
public enum ErrorCode {
    POINT_NOT_FOUND(HttpStatus.NO_CONTENT,"POINT-01", "포인트가 존재하지 않습니다."),
    POINT_INSUFFICIENT(HttpStatus.BAD_REQUEST, "POINT-02", "포인트가 부족합니다."),


    USER_NOT_FOUND(HttpStatus.NO_CONTENT,"USER-01", "유저가 존재하지 않습니다."),


    CONCERT_NOT_FOUND(HttpStatus.NO_CONTENT,"CONCERT-01", "콘서트가 존재하지 않습니다."),
    RESERVATION_TIME_INVALID(HttpStatus.BAD_REQUEST,"CONCERT-02", "예약 기간이 아닙니다."),
    RESERVATION_DATE_NOT_FOUND(HttpStatus.NO_CONTENT,"CONCERT-03", "예약 가능한 날짜가 없습니다."),
    RESERVATION_SEAT_NOT_FOUND(HttpStatus.NO_CONTENT,"CONCERT-04", "예약 가능한 좌석이 없습니다."),

    SEAT_NOT_FOUND(HttpStatus.NO_CONTENT,"SEAT-01", "유효하지 않는 좌석입니다."),


    SEAT_NOT_AVAILABLE(HttpStatus.CONFLICT, "RESERVATION-01", "이미 예약 완료된 좌석입니다."),
    RESERVATION_TIME_EXCEEDED(HttpStatus.BAD_REQUEST, "RESERVATION-02", "예약 시간이 초과되었습니다."),


    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-01", "토큰이 만료되었습니다."),
    TOKEN_PENDING(HttpStatus.FORBIDDEN, "AUTH-02", "토큰이 아직 활성화되지 않았습니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "AUTH-03", "정상적인 토큰이 아닙니다.");



    private final HttpStatus status;

    private final String code;

    private final String message;

}
