package kr.hhplus.be.server.api.point.domain.enums;

import kr.hhplus.be.server.api.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PointErrorCode implements ErrorCode {

    POINT_NOT_FOUND(HttpStatus.NO_CONTENT,"POINT-01", "포인트가 존재하지 않습니다."),
    POINT_INSUFFICIENT(HttpStatus.BAD_REQUEST, "POINT-02", "포인트가 부족합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    PointErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
