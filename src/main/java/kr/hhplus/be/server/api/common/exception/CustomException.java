package kr.hhplus.be.server.api.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.api.common.exception.enums.ErrorCode;
import lombok.Getter;

@Schema(description = "커스텀 예외 클래스")
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
