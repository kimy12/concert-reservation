package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertRepository concertRepository;


    @DisplayName("콘서트 ID로 콘서트 정보를 조회한다.")
    @Test
    void findConcertInfoById() {
        // given
        long concertId = 1L;
        List<ConcertInfoModel> mockConcertInfo = List.of(
                new ConcertInfoModel(concertId, "Concert A", 1L, 1,null),
                new ConcertInfoModel(concertId, "Concert A", 2L, 2,null)
        );

        when(concertRepository.getConcertInfo(concertId)).thenReturn(mockConcertInfo);

        // when
        List<ConcertInfoModel> result = concertService.findConcertInfoById(concertId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("title").containsExactly("Concert A", "Concert A");
        verify(concertRepository, times(1)).getConcertInfo(concertId);
    }

    @DisplayName("존재하지 않는 콘서트 ID로 조회 시 예외를 던진다.")
    @Test
    void findConcertInfoByIdThrowsException() {
        // given
        long concertId = 1L;
        when(concertRepository.getConcertInfo(concertId)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> concertService.findConcertInfoById(concertId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(CONCERT_NOT_FOUND.getMessage());

        verify(concertRepository, times(1)).getConcertInfo(concertId);
    }

    @DisplayName("콘서트 ID로 예약 가능한 날짜를 조회한다.")
    @Test
    void findAvailableDates() {
        // given
        long concertId = 1L;
        List<ConcertScheduleModel> mockSchedules = List.of(
                new ConcertScheduleModel(concertId, "title1",1L, 1, LocalDateTime.now()),
                new ConcertScheduleModel(concertId, "title1",2L, 2, LocalDateTime.now())
        );

        when(concertRepository.getAvailableDates(concertId)).thenReturn(mockSchedules);

        // when
        List<ConcertScheduleModel> result = concertService.findAvailableDates(concertId);

        // then
        assertThat(result).hasSize(2);
        verify(concertRepository, times(1)).getAvailableDates(concertId);
    }

    @DisplayName("예약 가능한 날짜가 없을 경우 예외를 던진다.")
    @Test
    void findAvailableDatesThrowsException() {
        // given
        long concertId = 1L;
        when(concertRepository.getAvailableDates(concertId)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> concertService.findAvailableDates(concertId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(RESERVATION_DATE_NOT_FOUND.getMessage());

        verify(concertRepository, times(1)).getAvailableDates(concertId);
    }

    @DisplayName("콘서트 ID로 예약 가능한 좌석을 조회한다.")
    @Test
    void findAvailableSeats() {
        // given
        long concertId = 1L;
        List<ConcertSeatModel> mockSeats = List.of(
                new ConcertSeatModel(1L, 1L, 1L, 10000L, 30, null),
                new ConcertSeatModel(2L, 2L, 1L, 12000L, 31, null)
        );

        when(concertRepository.getAvailableSeats(concertId)).thenReturn(mockSeats);

        // when
        List<ConcertSeatModel> result = concertService.findAvailableSeats(concertId);

        // then
        assertThat(result).hasSize(2);
        verify(concertRepository, times(1)).getAvailableSeats(concertId);
    }

    @DisplayName("예약 가능한 좌석이 없을 경우 예외를 던진다.")
    @Test
    void findAvailableSeatsThrowsException() {
        // given
        long concertId = 1L;
        when(concertRepository.getAvailableSeats(concertId)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> concertService.findAvailableSeats(concertId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(RESERVATION_SEAT_NOT_FOUND.getMessage());

        verify(concertRepository, times(1)).getAvailableSeats(concertId);
    }
}