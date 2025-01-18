package kr.hhplus.be.server.api.point.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.api.common.exception.CustomException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static kr.hhplus.be.server.api.point.domain.enums.PointErrorCode.POINT_INSUFFICIENT;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class UserPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private long totalPoint;

    @OneToMany(mappedBy = "userPoint", cascade = CascadeType.ALL)
    @Setter
    private List<PointHistory> pointHistory = new ArrayList<>();

    public void addPointHistory(PointHistory pointHistory) {
        this.pointHistory.add(pointHistory);
        pointHistory.setUserPoint(this);
    }

    @Builder
    public UserPoint(long id, long userId, long totalPoint) {
        this.id = id;
        this.userId = userId;
        this.totalPoint = totalPoint;
    }

    public long addPoints(long amount) {
        return this.totalPoint += amount;
    }

    public long deductPoint(long amount) {
        if(checkPointBeforeDeducting(amount)) {
            this.totalPoint -= amount;
        }
        return this.totalPoint;
    }

    public boolean checkPointBeforeDeducting (long amount) {
        if(this.totalPoint < amount) {
            throw new CustomException(POINT_INSUFFICIENT);
        }
        return true;
    }
}
