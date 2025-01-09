package kr.hhplus.be.server.api.concert.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.common.response.RestResponse;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import kr.hhplus.be.server.api.point.presentation.dto.PointRequest;
import kr.hhplus.be.server.api.point.presentation.dto.PointResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "[콘서트 API]")
public interface SwaggerApi {

    @Operation(summary = "콘서트 검색",
            responses = {
                    @ApiResponse(responseCode = "200", description = "콘서트 검색 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PointResponse.TotalPoint.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "code": 200,
                                                        "status": "100 CONTINUE",
                                                        "message": "string",
                                                        "data": [
                                                          {
                                                            "concertId": 5,
                                                            "title": "string",
                                                            "scheduleId": 5,
                                                            "seq": 5,
                                                            "concertSchedule": "2025-01-09T18:13:56.729Z"
                                                          }
                                                        ]
                                                      }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "204", description = "콘서트가 존재하지 않습니다.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomException.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status" : "204",
                                                      "code": "CONCERT-01",
                                                      "message": "콘서트가 존재하지 않습니다."
                                                    }
                                            """
                                    )
                            ))
            })
    RestResponse<List<ConcertResponse.ConcertInfo>> concertInfo(long concertId);

    @Operation(summary = "예약 가능한 날짜 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 가능한 날짜 조회 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PointResponse.TotalPoint.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                       "code": 200,
                                                       "status": "100 CONTINUE",
                                                       "message": "string",
                                                       "data": [
                                                         {
                                                           "concertId": 1,
                                                           "title": "string",
                                                           "seq": 1,
                                                           "scheduleId": 1,
                                                           "availableDate": "2025-01-09T18:14:17.679Z"
                                                         }
                                                       ]
                                                     }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "204", description = "예약 가능한 날짜가 없습니다.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomException.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status" : "204",
                                                      "code": "CONCERT-02",
                                                      "message": "예약 가능한 날짜가 없습니다."
                                                    }
                                            """
                                    )
                            ))
            })
    RestResponse<List<ConcertResponse.AvailableDates>> availableDates(long concertId);

    @Operation(summary = "예약 가능한 좌석 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 가능한 좌석 조회 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PointResponse.TotalPoint.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                       "code": 200,
                                                       "status": "100 CONTINUE",
                                                       "message": "string",
                                                       "data": [
                                                         {
                                                           "concertId": 1,
                                                           "seatId": 2,
                                                           "scheduleId": 1,
                                                           "seatNumber": 5,
                                                           "price": 5000
                                                         }
                                                       ]
                                                     }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "204", description = "예약 가능한 좌석이 없습니다.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomException.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status" : "204",
                                                      "code": "CONCERT-04",
                                                      "message": "예약 가능한 좌석이 없습니다."
                                                    }
                                            """
                                    )
                            ))
            })
    RestResponse<List<ConcertResponse.SeatInfo>> availableSeats( long scheduleId);

    @Operation(summary = "콘서트 자리 예약",
            responses = {
                    @ApiResponse(responseCode = "200", description = "콘서트 자리 예약 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PointResponse.TotalPoint.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "code": 200,
                                                      "status": "100 CONTINUE",
                                                      "message": "string",
                                                      "data": {
                                                        "scheduleId": 1,
                                                        "seatNumber": 1,
                                                        "price": 5000,
                                                        "createAt": "2025-01-09T18:15:18.597Z"
                                                      }
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "409", description = "이미 예약 완료된 좌석입니다.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomException.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status" : "409",
                                                      "code": "RESERVATION-01",
                                                      "message": "이미 예약 완료된 좌석입니다."
                                                    }
                                            """
                                    )
                            ))
            })
    RestResponse<ConcertResponse.ReservedSeatInfo> reserveConcertSeat (ConcertRequest.ReserveConcert request);

    @Operation(summary = "예약 좌석 결제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 좌석 결제 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PointResponse.TotalPoint.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                       "userId": 1,
                                                       "scheduleId": 1,
                                                       "seatId": 5
                                                     }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "예약 시간이 초과되었습니다.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomException.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status" : "400",
                                                      "code": "RESERVATION-02",
                                                      "message": "예약 시간이 초과되었습니다."
                                                    }
                                            """
                                    )
                            ))
            })
    RestResponse<ConcertResponse.ReservedSeatInfo> payConcertSeat (ConcertRequest.ReserveConcert request);
}
