package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertInfoDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertScheduleDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertSeatDto;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertInfoDto> findConcertInfoById(long concertId) {
        return Optional.ofNullable(concertRepository.getConcertInfo(concertId))
                .orElseThrow(() -> new CustomException(CONCERT_NOT_FOUND))
                .stream()
                .toList();
    }

    public List<ConcertScheduleDto> findAvailableDates(long concertId) {
        return Optional.ofNullable(concertRepository.getAvailableDates(concertId))
                .orElseThrow(() -> new CustomException(RESERVATION_DATE_NOT_FOUND))
                .stream()
                .toList();
    }

    public List<ConcertSeatDto> findAvailableSeats(long concertId) {
        return Optional.ofNullable(concertRepository.getAvailableSeats(concertId))
                .orElseThrow(() -> new CustomException(RESERVATION_SEAT_NOT_FOUND))
                .stream()
                .toList();
    }
}
