package kr.hhplus.be.server.api.common.exception;

import kr.hhplus.be.server.api.common.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiErrorResponse> handleCustomException(CustomException e) {
        return ApiErrorResponse.to(e.getErrorCodeBack());
     }
}
