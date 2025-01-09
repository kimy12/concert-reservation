package kr.hhplus.be.server.api.point.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.common.response.RestResponse;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;

@Tag(name = "[포인트 API]")
public interface SwaggerApi {

    @Operation(summary = "사용자 포인트 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 포인트 조회 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PointResponse.TotalPoint.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "userId": 1,
                                                      "totalPoint": 200000
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "204", description = "포인트가 존재하지 않습니다.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomException.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status" : "204",
                                                      "code": "POINT-01",
                                                      "message": "포인트가 존재하지 않습니다."
                                                    }
                                            """
                                    )
                            ))
            })
    RestResponse<PointResponse.TotalPoint> getTotalPoint(long userId);

    @Operation(summary = "사용자 포인트 충전 및 사용",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 포인트 충전 및 사용 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PointResponse.TotalPoint.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "userId": 1,
                                                      "totalPoint": 200000
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "포인트가 부족합니다.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomException.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status" : "400",
                                                      "code": "POINT-02",
                                                      "message": "포인트가 부족합니다."
                                                    }
                                            """
                                    )
                            ))
            })
    RestResponse<PointResponse.TotalPoint> chargePoint(PointRequest.ChargePoint pointRequest);
}
