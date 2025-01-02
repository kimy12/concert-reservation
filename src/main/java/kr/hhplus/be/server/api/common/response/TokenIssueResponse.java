package kr.hhplus.be.server.api.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TokenIssueResponse<T> {
    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    public TokenIssueResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> TokenIssueResponse<T> of(HttpStatus httpStatus, T data) {
        return new TokenIssueResponse<>(httpStatus, httpStatus.name(), data);
    }

    public static <T> TokenIssueResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }
}
