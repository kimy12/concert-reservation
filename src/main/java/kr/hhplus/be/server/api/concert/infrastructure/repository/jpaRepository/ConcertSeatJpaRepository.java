package kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cs " +
            "FROM ConcertSeat cs " +
            "WHERE cs.seatNumber = :seatNumber " +
            "AND cs.scheduleId = :scheduleId " +
            "AND (cs.status = :status OR cs.status is null)")
    Optional<ConcertSeat> findBySeatNumberAndScheduleIdAndStatus(@Param("seatNumber") Long seatNumber, @Param("scheduleId")Long scheduleId,@Param("status") ConcertSeatStatus status);

    @Modifying
    @Query("UPDATE ConcertSeat cs " +
            "SET cs.status = :newStatus " +
            "WHERE cs.seatNumber = :seatNumber " +
            "AND cs.scheduleId = :scheduleId ")
    int updateSeatStatus (@Param("seatNumber") Long seatNumber, @Param("scheduleId")Long scheduleId, @Param("newStatus")ConcertSeatStatus newStatus);
}
