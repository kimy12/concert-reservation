package kr.hhplus.be.server.api.token.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.common.response.RestResponse;
import kr.hhplus.be.server.api.common.response.TokenIssueResponse;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import kr.hhplus.be.server.api.token.presentation.dto.TokenRequest;
import kr.hhplus.be.server.api.token.presentation.dto.TokenResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "[토큰 API]")
public interface SwaggerApi {

    @Operation(summary = "사용자 토큰 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 토큰 조회 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PointResponse.TotalPoint.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "code": 0,
                                                      "status": "100 CONTINUE",
                                                      "message": "string",
                                                      "data": {
                                                        "token": "string",
                                                        "status": "string"
                                                      }
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "정상적인 토큰이 아닙니다.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomException.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status" : "400",
                                                      "code": "AUTH-03",
                                                      "message": "정상적인 토큰이 아닙니다."
                                                    }
                                            """
                                    )
                            ))
            })
    public TokenIssueResponse<TokenResponse.Response> getToken(TokenRequest.Request request);
}
