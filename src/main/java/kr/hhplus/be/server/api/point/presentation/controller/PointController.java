package kr.hhplus.be.server.api.point.presentation.controller;

import kr.hhplus.be.server.api.common.response.RestResponse;
import kr.hhplus.be.server.api.point.application.UserPointFacade;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/point")
public class PointController implements SwaggerApi{

    private final UserPointFacade userPointFacade;

    @Override
    @GetMapping("/api/v1/{userId}/totalPoint")
    public RestResponse<PointResponse.TotalPoint> getTotalPoint(@PathVariable(name = "userId") long userId) {
        return RestResponse.of(HttpStatus.OK, userPointFacade.getUserPoint(userId));
    }

    @Override
    @PatchMapping("/api/v1/chargePoint")
    public RestResponse<PointResponse.TotalPoint> chargePoint(@RequestBody PointRequest.ChargePoint pointRequest) {
        return RestResponse.ok(userPointFacade.chargePoint(pointRequest));
    }
}
