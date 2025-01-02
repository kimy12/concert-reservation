package kr.hhplus.be.server.api.concert.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ConcertController.class)
class ConcertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @DisplayName("콘서트를 검색한다.")
    @Test
    void getConcertByName() throws Exception {
        // given
        String title = "콘서트";

        // when // then
        mockMvc.perform(get("/concerts/api/v1")
                        .param("title", title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.concertId").value(1))
                .andExpect(jsonPath("$.data.title").value("콘서트"))
                .andExpect(jsonPath("$.data.startAt").value("2024-05-05T14:00:00"))
                ;

    }

    @DisplayName("예약 가능한 날짜를 가져온다.")
    @Test
    void availableDates() throws Exception{
        long concertId = 1L;
        // when // then
        mockMvc.perform(
                        get("/concerts/api/v1/{concertId}/availableDates", concertId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concertId").value("1"))
                .andExpect(jsonPath("$.data.availableDates[0]").value("2024-05-05T14:00:00"))
                .andExpect(jsonPath("$.data.availableDates[1]").value("2024-05-06T14:00:00"));
    }

    @DisplayName("예약 가능한 좌석을 가져온다.")
    @Test
    void availableSeats() throws Exception{
        long concertId = 1L;
        LocalDateTime date = LocalDateTime.now();
        // when // then
        mockMvc.perform(
                        get("/concerts/api/v1/{concertId}/availableSeats/{date}", concertId, date)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concertId").value("1"))
                .andExpect(jsonPath("$.data.seatNumber[0]").value("1"))
                .andExpect(jsonPath("$.data.seatNumber[1]").value("2"))
                .andExpect(jsonPath("$.data.seatNumber[2]").value("3"))
                .andExpect(jsonPath("$.data.seatNumber[3]").value("20"))
                .andExpect(jsonPath("$.data.price").value("5000"));
    }

    @DisplayName("콘서트 자리를 예약한다.")
    @Test
    void reserveSeat() throws Exception{
        // when // then
        mockMvc.perform(
                        post("/concerts/api/v1/reserveSeat")
                                .content(objectMapper.writeValueAsString(new ConcertRequest.ReserveConcert(1L, 1L, 30)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concertId").value("1"))
                .andExpect(jsonPath("$.data.seatNumber").value("3"))
                .andExpect(jsonPath("$.data.price").value("5000"));
    }
}