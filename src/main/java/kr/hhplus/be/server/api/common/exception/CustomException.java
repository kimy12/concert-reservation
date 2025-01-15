package kr.hhplus.be.server.api.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "커스텀 예외 클래스")
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCodeBack;

    public CustomException(ErrorCode errorCodeBack) {
        super(errorCodeBack.getMessage());
        this.errorCodeBack = errorCodeBack;
    }
}
