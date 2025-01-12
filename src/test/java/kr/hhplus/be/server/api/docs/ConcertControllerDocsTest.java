//package kr.hhplus.be.server.api.docs;
//
//import kr.hhplus.be.server.RestDocsSupport;
//import kr.hhplus.be.server.api.concert.application.ConcertFacade;
//import kr.hhplus.be.server.api.concert.presentation.controller.ConcertController;
//import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
//import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
//import kr.hhplus.be.server.api.point.application.UserPointFacade;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.payload.JsonFieldType;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.Mockito.mock;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//public class ConcertControllerDocsTest extends RestDocsSupport {
//    private final ConcertFacade concertFacade = mock(ConcertFacade.class);
//
//    @Override
//    protected Object initController() {
//        return new ConcertController(concertFacade);
//    }
//
//    @DisplayName("콘서트를 검색 API")
//    @Test
//    void getConcertByName() throws Exception {
//        long concertId = 1L;
//
//        mockMvc.perform(get("/concerts/api/v1")
//                        .param("concertId", String.valueOf(concertId))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.data.concertId").value(1))
//                .andExpect(jsonPath("$.data.title").value("콘서트"))
//                .andExpect(jsonPath("$.data.concertSchedule[0]").value("2024-05-05T14:00:00"))
//                .andExpect(jsonPath("$.data.concertSchedule[1]").value("2024-05-05T13:00:00"))
//                .andDo(document("concert-get",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        queryParameters(
//                                parameterWithName("concertId").description("콘서트 아이디")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("응답 코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("응답 상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("응답 메시지"),
//                                fieldWithPath("data.concertId").type(JsonFieldType.NUMBER)
//                                        .description("콘서트 아이디"),
//                                fieldWithPath("data.title").type(JsonFieldType.STRING)
//                                        .description("콘서트 이름"),
//                                fieldWithPath("data.concertSchedule").type(JsonFieldType.ARRAY)
//                                        .description("콘서트 스케줄")
//                        )
//                ));
//
//    }
//
//    @DisplayName("가능날짜 검색 API")
//    @Test
//    void availableDates() throws Exception {
//        //given
//        long concertId = 1L;
//
//        ConcertResponse.AvailableDates date1 = ConcertResponse.AvailableDates.builder()
//                .scheduleId(1)
//                .availableDate(LocalDateTime.of(2024, 5, 5, 14, 13))
//                .build();
//
//        ConcertResponse.AvailableDates date2 = ConcertResponse.AvailableDates.builder()
//                .scheduleId(2)
//                .availableDate(LocalDateTime.of(2024, 5, 6, 14, 13))
//                .build();
//
//        List<ConcertResponse.AvailableDates> availableDates = List.of(date1, date2);
//
//
//        Mockito.when(concertFacade.getAvailableDates(concertId)).thenReturn(availableDates);
//        mockMvc.perform(
//                        get("/concerts/api/v1/{concertId}/availableDates", concertId)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].scheduleId").value("1"))
//                .andExpect(jsonPath("$.data[1].scheduleId").value("2"))
//                .andExpect(jsonPath("$.data[0].availableDate").value("2024-05-05T14:13:00"))
//                .andExpect(jsonPath("$.data[1].availableDate").value("2024-05-06T14:13:00"))
//                .andDo(document("availableDates-get",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("concertId").description("콘서트 아이디")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("응답 코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("응답 상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("응답 메시지"),
//                                fieldWithPath("data").type(JsonFieldType.ARRAY)
//                                        .description("예약가능 날짜 정보"),
//                                fieldWithPath("data[0].scheduleId").type(JsonFieldType.NUMBER)
//                                        .description("콘서트 스케줄 아이디"),
//                                fieldWithPath("data[0].availableDate").type(JsonFieldType.STRING)
//                                        .description("예약 가능 날짜")
//                        )
//                ));
//    }
//
//    @DisplayName("가능좌석 검색 API")
//    @Test
//    void availableSeats() throws Exception {
//        long concertId = 1L;
//        LocalDateTime date = LocalDateTime.of(2024, 5, 5, 14, 13);
//
//        ConcertResponse.SeatInfo seat1 = ConcertResponse.SeatInfo.builder()
//                .seatId(1)
//                .scheduleId(1)
//                .seatNumber(1)
//                .price(5000L)
//                .build();
//
//        ConcertResponse.SeatInfo seat2 = ConcertResponse.SeatInfo.builder()
//                .seatId(2)
//                .scheduleId(1)
//                .seatNumber(2)
//                .price(6000L)
//                .build();
//
//        List<ConcertResponse.SeatInfo> availableSeats = List.of(seat1, seat2);
//
//        Mockito.when(concertFacade.getAvailableSeats(concertId, date)).thenReturn(availableSeats);
//        mockMvc.perform(
//                        get("/concerts/api/v1/{concertId}/availableSeats/{date}", concertId, date)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].seatId").value("1"))
//                .andExpect(jsonPath("$.data[0].scheduleId").value("1"))
//                .andExpect(jsonPath("$.data[0].seatNumber").value("1"))
//                .andExpect(jsonPath("$.data[0].price").value("5000"))
//                .andExpect(jsonPath("$.data[1].seatId").value("2"))
//                .andExpect(jsonPath("$.data[1].scheduleId").value("1"))
//                .andExpect(jsonPath("$.data[1].seatNumber").value("2"))
//                .andExpect(jsonPath("$.data[1].price").value("6000"))
//                .andDo(document("availableSeats-get",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("concertId").description("콘서트 아이디"),
//                                parameterWithName("date").description("콘서트 날짜")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("응답 코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("응답 상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("응답 메시지"),
//                                fieldWithPath("data").type(JsonFieldType.ARRAY)
//                                        .description("콘서트 좌석 정보"),
//                                fieldWithPath("data[0].seatId").type(JsonFieldType.NUMBER)
//                                        .description("좌석 아이디"),
//                                fieldWithPath("data[0].scheduleId").type(JsonFieldType.NUMBER)
//                                        .description("콘서트 스케줄 아이디"),
//                                fieldWithPath("data[0].seatNumber").type(JsonFieldType.NUMBER)
//                                        .description("예약 가능 좌석"),
//                                fieldWithPath("data[0].price").type(JsonFieldType.NUMBER)
//                                        .description("가격")
//                        )
//                ));
//    }
//
//    @DisplayName("좌석 예약 API")
//    @Test
//    void reserveSeat() throws Exception {
//        mockMvc.perform(
//                        post("/concerts/api/v1/reserveSeat")
//                                .content(objectMapper.writeValueAsString(new ConcertRequest.ReserveConcert(1L, 1L, 30)))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.concertId").value("1"))
//                .andExpect(jsonPath("$.data.seatNumber").value("3"))
//                .andExpect(jsonPath("$.data.price").value("5000"))
//                .andDo(document("seat-reserve",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("userId").type(JsonFieldType.NUMBER)
//                                        .description("유저 아이디"),
//                                fieldWithPath("concertId").type(JsonFieldType.NUMBER)
//                                        .description("콘서트 아이디"),
//                                fieldWithPath("seatNo").type(JsonFieldType.NUMBER)
//                                        .description("좌석 번호")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("응답 코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("응답 상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("응답 메시지"),
//                                fieldWithPath("data.concertId").type(JsonFieldType.NUMBER)
//                                        .description("유저 아이디"),
//                                fieldWithPath("data.seatNumber").type(JsonFieldType.NUMBER)
//                                        .description("좌석 번호"),
//                                fieldWithPath("data.price").type(JsonFieldType.NUMBER)
//                                        .description("가격")
//                        )
//                ));
//    }
//
//}
