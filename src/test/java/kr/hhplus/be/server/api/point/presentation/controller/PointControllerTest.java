package kr.hhplus.be.server.api.point.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.common.interceptor.TokenInterceptor;
import kr.hhplus.be.server.api.point.application.UserPointFacade;
import kr.hhplus.be.server.api.point.domain.enums.PointHistoryType;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import kr.hhplus.be.server.api.token.domain.service.TokenService;
import kr.hhplus.be.server.config.WebMvcConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.CHARGE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PointController.class)
class PointControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected UserPointFacade userPointFacade;

    @MockitoBean
    private TokenInterceptor tokenInterceptor;

    @Autowired
    protected ObjectMapper objectMapper;

    @DisplayName("포인트를 조회한다.")
    @Test
    void getUserPoint() throws Exception {
        // given
        long userId = 1L;

        PointResponse.TotalPoint point = PointResponse.TotalPoint.builder()
                .userId(userId)
                .totalPoint(5000L)
                .build();

        when(userPointFacade.getUserPoint(userId)).thenReturn(point);

        // when // then
        mockMvc.perform(
                        get("/point/api/v1/{userId}/totalPoint", userId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1L))
                .andExpect(jsonPath("$.data.totalPoint").value(5000L));
    }

    @DisplayName("포인트를 충전한다.")
    @Test
    void chargePoint() throws Exception {

        // given
        long userId = 1L;
        PointRequest.ChargePoint chargeInfo = PointRequest.ChargePoint.builder()
                .amount(5000L)
                .userId(userId)
                .build();
        PointResponse.TotalPoint totalPoint = PointResponse.TotalPoint.builder()
                .userId(userId)
                .totalPoint(5000L)
                .build();
        when(userPointFacade.chargePoint(chargeInfo)).thenReturn(totalPoint);

        // when // then
        mockMvc.perform(
                        patch("/point/api/v1/chargePoint")
                                .content(objectMapper.writeValueAsString(chargeInfo))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.totalPoint").value(5000L));
    }
}