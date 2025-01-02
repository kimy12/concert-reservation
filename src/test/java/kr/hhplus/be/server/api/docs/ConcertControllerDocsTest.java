package kr.hhplus.be.server.api.docs;

import kr.hhplus.be.server.RestDocsSupport;
import kr.hhplus.be.server.api.concert.presentation.controller.ConcertController;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ConcertControllerDocsTest extends RestDocsSupport {


    @Override
    protected Object initController() {
        return new ConcertController();
    }

    @DisplayName("콘서트를 검색 API")
    @Test
    void getConcertByName() throws Exception {
        String title = "콘서트";

        mockMvc.perform(get("/concerts/api/v1")
                        .param("title", title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.concertId").value(1))
                .andExpect(jsonPath("$.data.title").value("콘서트"))
                .andExpect(jsonPath("$.data.startAt").value("2024-05-05T14:00:00"))
                .andDo(document("concert-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("title").description("콘서트 이름")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메시지"),
                                fieldWithPath("data.concertId").type(JsonFieldType.NUMBER)
                                        .description("콘서트 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING)
                                        .description("콘서트 이름"),
                                fieldWithPath("data.startAt").type(JsonFieldType.STRING)
                                        .description("콘서트 날짜")
                        )
                ));

    }

    @DisplayName("가능날짜 검색 API")
    @Test
    void availableDates() throws Exception {
        long concertId = 1L;
        mockMvc.perform(
                        get("/concerts/api/v1/{concertId}/availableDates", concertId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concertId").value("1"))
                .andExpect(jsonPath("$.data.availableDates[0]").value("2024-05-05T14:00:00"))
                .andExpect(jsonPath("$.data.availableDates[1]").value("2024-05-06T14:00:00"))
                .andDo(document("availableDates-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("concertId").description("콘서트 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메시지"),
                                fieldWithPath("data.concertId").type(JsonFieldType.NUMBER)
                                        .description("콘서트 아이디"),
                                fieldWithPath("data.availableDates").type(JsonFieldType.ARRAY)
                                        .description("예약 가능 좌석")
                        )
                ));
    }

    @DisplayName("가능좌석 검색 API")
    @Test
    void availableSeats() throws Exception {
        long concertId = 1L;
        LocalDateTime date = LocalDateTime.now();
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
                .andExpect(jsonPath("$.data.price").value("5000"))
                .andDo(document("availableSeats-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("concertId").description("콘서트 아이디"),
                                parameterWithName("date").description("콘서트 날짜")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메시지"),
                                fieldWithPath("data.concertId").type(JsonFieldType.NUMBER)
                                        .description("콘서트 아이디"),
                                fieldWithPath("data.seatNumber").type(JsonFieldType.ARRAY)
                                        .description("예약 가능 좌석"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                                        .description("가격")
                        )
                ));
    }

    @DisplayName("좌석 예약 API")
    @Test
    void reserveSeat() throws Exception {
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
                .andExpect(jsonPath("$.data.price").value("5000"))
                .andDo(document("seat-reserve",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER)
                                        .description("유저 아이디"),
                                fieldWithPath("concertId").type(JsonFieldType.NUMBER)
                                        .description("콘서트 아이디"),
                                fieldWithPath("seatNo").type(JsonFieldType.NUMBER)
                                        .description("좌석 번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메시지"),
                                fieldWithPath("data.concertId").type(JsonFieldType.NUMBER)
                                        .description("유저 아이디"),
                                fieldWithPath("data.seatNumber").type(JsonFieldType.NUMBER)
                                        .description("좌석 번호"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                                        .description("가격")
                        )
                ));
    }

}
