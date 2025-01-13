package kr.hhplus.be.server.api.concert.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.concert.application.ConcertFacade;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ConcertController.class)
class ConcertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConcertFacade concertFacade;

    @Autowired
    protected ObjectMapper objectMapper;

    @DisplayName("콘서트를 검색한다.")
    @Test
    void getConcertByName() throws Exception {
        // given
        long concertId = 1L;

        // when // then
        mockMvc.perform(get("/concerts/api/v1")
                        .param("concertId", String.valueOf(concertId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.concertId").value(1))
                .andExpect(jsonPath("$.data.title").value("콘서트"))
                .andExpect(jsonPath("$.data.concertSchedule[0]").value("2024-05-05T14:00:00"))
                .andExpect(jsonPath("$.data.concertSchedule[1]").value("2024-05-05T13:00:00"))
                ;

    }

    @DisplayName("예약 가능한 날짜를 가져온다.")
    @Test
    void availableDates() throws Exception{
        //given
        long concertId = 1L;

        ConcertResponse.AvailableDates date1 = ConcertResponse.AvailableDates.builder()
                .scheduleId(1)
                .availableDate(LocalDateTime.of(2024, 5, 5, 14, 13))
                .build();

        ConcertResponse.AvailableDates date2 = ConcertResponse.AvailableDates.builder()
                .scheduleId(2)
                .availableDate(LocalDateTime.of(2024, 5, 6, 14, 13))
                .build();

        List<ConcertResponse.AvailableDates> availableDates = List.of(date1, date2);


        Mockito.when(concertFacade.getAvailableDates(concertId)).thenReturn(availableDates);

        // when //then
        mockMvc.perform(
                        get("/concerts/api/v1/{concertId}/availableDates", concertId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].scheduleId").value("1"))
                .andExpect(jsonPath("$.data[1].scheduleId").value("2"))
                .andExpect(jsonPath("$.data[0].availableDate").value("2024-05-05T14:13:00"))
                .andExpect(jsonPath("$.data[1].availableDate").value("2024-05-06T14:13:00"));
    }

    @DisplayName("예약 가능한 좌석을 가져온다.")
    @Test
    void availableSeats() throws Exception{
        // given
        long concertId = 1L;
        LocalDateTime date = LocalDateTime.of(2024, 5, 5, 14, 13);

        ConcertResponse.SeatInfo seat1 = ConcertResponse.SeatInfo.builder()
                .seatId(1)
                .scheduleId(1)
                .seatNumber(1)
                .price(5000L)
                .build();

        ConcertResponse.SeatInfo seat2 = ConcertResponse.SeatInfo.builder()
                .seatId(2)
                .scheduleId(1)
                .seatNumber(2)
                .price(6000L)
                .build();

        List<ConcertResponse.SeatInfo> availableSeats = List.of(seat1, seat2);

        Mockito.when(concertFacade.getAvailableSeats(concertId)).thenReturn(availableSeats);

        // when // then
        mockMvc.perform(
                        get("/concerts/api/v1/{concertId}/availableSeats", concertId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].seatId").value("1"))
                .andExpect(jsonPath("$.data[0].scheduleId").value("1"))
                .andExpect(jsonPath("$.data[0].seatNumber").value("1"))
                .andExpect(jsonPath("$.data[0].price").value("5000"))
                .andExpect(jsonPath("$.data[1].seatId").value("2"))
                .andExpect(jsonPath("$.data[1].scheduleId").value("1"))
                .andExpect(jsonPath("$.data[1].seatNumber").value("2"))
                .andExpect(jsonPath("$.data[1].price").value("6000"));

    }

    @DisplayName("콘서트 자리를 예약한다.")
    @Test
    void reserveSeat() throws Exception{
        // when // then
        mockMvc.perform(
                        post("/concerts/api/v1/reserveSeat")
                                .content(objectMapper.writeValueAsString(new ConcertRequest.ReserveConcert(1L, 1L, 30, 2)))
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