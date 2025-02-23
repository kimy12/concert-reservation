package kr.hhplus.be.server.api.concert.domain.enums;

import lombok.Getter;

@Getter
public enum OutboxStatus {
    CREATED, PUBLISHED, FAILED
}
