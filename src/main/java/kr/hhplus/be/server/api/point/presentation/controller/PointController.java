package kr.hhplus.be.server.api.point.presentation.controller;

import kr.hhplus.be.server.api.common.response.ApiResponse;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/point")
public class PointController {

    @GetMapping("/api/v1/{userId}/totalPoint")
    ApiResponse<PointResponse.TotalPoint> getTotalPoint(@PathVariable(name = "userId") long userId) {
        return ApiResponse.of(HttpStatus.OK, PointResponse.TotalPoint.builder()
                .userId(1)
                .totalPoint(100000)
                .build());
    }

    @PatchMapping("/api/v1/chargePoint")
    ApiResponse<PointResponse.TotalPoint> chargePoint(@RequestBody PointRequest.ChargePoint pointRequest) {
        return ApiResponse.ok(PointResponse.TotalPoint.builder()
                .userId(pointRequest.userId())
                .totalPoint(200000)
                .build());
    }
}
