package kr.hhplus.be.server.api.point.domain.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPoint is a Querydsl query type for UserPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserPoint extends EntityPathBase<UserPoint> {

    private static final long serialVersionUID = -1564190133L;

    public static final QUserPoint userPoint = new QUserPoint("userPoint");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<PointHistory, QPointHistory> pointHistory = this.<PointHistory, QPointHistory>createList("pointHistory", PointHistory.class, QPointHistory.class, PathInits.DIRECT2);

    public final NumberPath<Long> totalPoint = createNumber("totalPoint", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserPoint(String variable) {
        super(UserPoint.class, forVariable(variable));
    }

    public QUserPoint(Path<? extends UserPoint> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserPoint(PathMetadata metadata) {
        super(UserPoint.class, metadata);
    }

}

