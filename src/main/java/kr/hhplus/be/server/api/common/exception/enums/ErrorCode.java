package kr.hhplus.be.server.api.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    POINT_NOT_FOUND(HttpStatus.NO_CONTENT,"POINT-01", "포인트가 존재하지 않습니다."),

    POINT_INSUFFICIENT(HttpStatus.BAD_REQUEST, "POINT-02", "포인트가 부족합니다."),

    USER_NOT_FOUND(HttpStatus.NO_CONTENT,"USER-01", "유저가 존재하지 않습니다.");

    private final HttpStatus status;

    private final String code;

    private final String message;

}
