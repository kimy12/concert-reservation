package kr.hhplus.be.server.api.user.domain.repository;

import kr.hhplus.be.server.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
