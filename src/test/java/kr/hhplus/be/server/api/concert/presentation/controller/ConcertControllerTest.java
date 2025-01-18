package kr.hhplus.be.server.api.concert.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.concert.application.ConcertFacade;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import kr.hhplus.be.server.api.token.domain.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ConcertController.class)
class ConcertControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected ConcertFacade concertFacade;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected TokenService tokenService;

    @DisplayName("콘서트를 검색한다.")
    @Test
    void getConcertByName() throws Exception {
        // given
        long concertId = 1L;
        LocalDateTime now = LocalDateTime.now();

        ConcertResponse.ConcertInfo mockConcertInfo1 = ConcertResponse.ConcertInfo.builder()
                .concertId(1L)
                .title("콘서트")
                .scheduleId(1L)
                .seq(1L)
                .concertSchedule(LocalDateTime.of(2024, 5, 5, 14, 0))
                .build();

        ConcertResponse.ConcertInfo mockConcertInfo2 = ConcertResponse.ConcertInfo.builder()
                .concertId(1L)
                .title("콘서트")
                .scheduleId(2L)
                .seq(2L)
                .concertSchedule(LocalDateTime.of(2024, 5, 5, 13, 0))
                .build();

        List<ConcertResponse.ConcertInfo> mockResponse = List.of(mockConcertInfo1, mockConcertInfo2);

        when(concertFacade.getConcertInfo(concertId)).thenReturn(mockResponse);
        doNothing().when(tokenService).checkTokenQueue(any(UUID.class), any(LocalDateTime.class));

        // when // then
        mockMvc.perform(get("/concerts/api/v1")
                        .param("concertId", String.valueOf(concertId))
                        .header("USER-TOKEN", "123e4567-e89b-12d3-a456-426614174000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].concertId").value(1))
                .andExpect(jsonPath("$.data[0].title").value("콘서트"))
                .andExpect(jsonPath("$.data[0].concertSchedule").value("2024-05-05T14:00:00"))
                .andExpect(jsonPath("$.data[1].concertId").value(1))
                .andExpect(jsonPath("$.data[1].title").value("콘서트"))
                .andExpect(jsonPath("$.data[1].concertSchedule").value("2024-05-05T13:00:00"));

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


        when(concertFacade.getAvailableDates(concertId)).thenReturn(availableDates);
        doNothing().when(tokenService).checkTokenQueue(any(UUID.class), any(LocalDateTime.class));

        // when //then
        mockMvc.perform(
                        get("/concerts/api/v1/{concertId}/availableDates", concertId)
                                .header("USER-TOKEN", "123e4567-e89b-12d3-a456-426614174000")
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

        when(concertFacade.getAvailableSeats(concertId)).thenReturn(availableSeats);
        doNothing().when(tokenService).checkTokenQueue(any(UUID.class), any(LocalDateTime.class));

        // when // then
        mockMvc.perform(
                        get("/concerts/api/v1/{concertId}/availableSeats", concertId)
                                .header("USER-TOKEN", "123e4567-e89b-12d3-a456-426614174000")
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
        // given
        ConcertRequest.ReserveConcert reserveConcert = new ConcertRequest.ReserveConcert(1L, 1L, 30);
        ConcertResponse.ReservedSeatInfo result = ConcertResponse.ReservedSeatInfo.builder()
                .scheduleId(1L)
                .createAt(LocalDateTime.of(2024, 5, 5, 14, 0))
                .seatNumber(30)
                .price(5000L)
                .build();

        doNothing().when(tokenService).checkTokenQueue(any(UUID.class), any(LocalDateTime.class));
        when(concertFacade.reservedSeat(reserveConcert)).thenReturn(result);

        // when // then
        mockMvc.perform(
                        post("/concerts/api/v1/reserveSeat")
                                .content(objectMapper.writeValueAsString(reserveConcert))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("USER-TOKEN", "123e4567-e89b-12d3-a456-426614174000")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.scheduleId").value(1))
                .andExpect(jsonPath("$.data.seatNumber").value(30));
    }
}