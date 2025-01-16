package kr.hhplus.be.server.api.token.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QToken is a Querydsl query type for Token
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QToken extends EntityPathBase<Token> {

    private static final long serialVersionUID = -109569117L;

    public static final QToken token = new QToken("token");

    public final DateTimePath<java.time.LocalDateTime> expireAt = createDateTime("expireAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<kr.hhplus.be.server.api.token.domain.enums.TokenStatus> tokenStatus = createEnum("tokenStatus", kr.hhplus.be.server.api.token.domain.enums.TokenStatus.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QToken(String variable) {
        super(Token.class, forVariable(variable));
    }

    public QToken(Path<? extends Token> path) {
        super(path.getType(), path.getMetadata());
    }

    public QToken(PathMetadata metadata) {
        super(Token.class, metadata);
    }

}

