package kr.hhplus.be.server.api.common.response;

import kr.hhplus.be.server.api.common.exception.enums.ErrorCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ApiErrorResponse {
    private int status;
    private String name;
    private String code;
    private String message;

    public static ResponseEntity<ApiErrorResponse> to (ErrorCode e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ApiErrorResponse.builder()
                        .status(e.getStatus().value())
                        .name(e.name())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}