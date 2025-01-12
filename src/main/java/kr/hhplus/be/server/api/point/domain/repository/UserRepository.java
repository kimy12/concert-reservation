package kr.hhplus.be.server.api.point.domain.repository;

import kr.hhplus.be.server.api.point.domain.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
