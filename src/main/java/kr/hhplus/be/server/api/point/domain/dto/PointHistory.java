package kr.hhplus.be.server.api.point.domain.dto;

import jakarta.persistence.*;
import kr.hhplus.be.server.api.point.domain.enums.PointHistoryType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long userPointId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userPointId") @Setter
    private UserPoint userPoint;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private PointHistoryType type;

    private LocalDateTime createAt;

    @Builder
    public PointHistory(Long id, UserPoint userPoint, Long amount, PointHistoryType type, LocalDateTime createAt) {
        this.id = id;
        this.userPoint = userPoint;
        this.amount = amount;
        this.type = type;
        this.createAt = createAt != null ? createAt : LocalDateTime.now();
    }
}
