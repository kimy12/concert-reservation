package kr.hhplus.be.server.api.point.domain.repository;

import kr.hhplus.be.server.api.point.domain.dto.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    Optional<UserPoint> findByUserId(long userId);
}
