package kr.hhplus.be.server.api.point.domain.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.api.point.domain.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT up FROM UserPoint up WHERE up.userId = :userId")
    Optional<UserPoint> findByUserIdWithLock(long userId);

    Optional<UserPoint> findByUserId(long userId);
}
