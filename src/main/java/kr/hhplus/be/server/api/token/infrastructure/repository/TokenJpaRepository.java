package kr.hhplus.be.server.api.token.infrastructure.repository;

import kr.hhplus.be.server.api.token.infrastructure.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

    @Modifying
    @Query("DELETE FROM Token t WHERE t.createdAt > :thisTime")
    int deleteTokensOlderThan(@Param("thisTime") LocalDateTime thisTime);
}