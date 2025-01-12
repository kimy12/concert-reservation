package kr.hhplus.be.server.api.point.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.point.application.UserPointFacade;
import kr.hhplus.be.server.api.point.domain.enums.PointHistoryType;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.CHARGE;
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

    @Autowired
    protected ObjectMapper objectMapper;

    @DisplayName("포인트를 조회한다.")
    @Test
    void getUserPoint() throws Exception {
        // given
        long userId = 1L;

        // when // then
        mockMvc.perform(
                        get("/point/api/v1/{userId}/totalPoint", userId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value("1"))
                .andExpect(jsonPath("$.data.totalPoint").value("100000"));
    }

    @DisplayName("포인트를 충전한다.")
    @Test
    void chargePoint() throws Exception {

        // when // then
        mockMvc.perform(
                        patch("/point/api/v1/chargePoint")
                                .content(objectMapper.writeValueAsString(new PointRequest.ChargePoint(1L, 100000, CHARGE)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value("1"))
                .andExpect(jsonPath("$.data.totalPoint").value("200000"));
    }
}