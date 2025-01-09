package kr.hhplus.be.server.api.token.util;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.common.exception.enums.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUUIDManager {

    private static final long CUSTOM_IDENTIFIER = 0xCAFEBABEL;

    public UUID createUuidWithLong(long inputLong) {
        long msb = (inputLong & 0xFFFFFFFFFFFF0000L) | (CUSTOM_IDENTIFIER & 0xFFFFL);
        long lsb = UUID.randomUUID().getLeastSignificantBits();
        return new UUID(msb, lsb);
    }

    public long getTokenIdByTokenUuid(UUID tokenUuid) {
        long msb = tokenUuid.getMostSignificantBits();
        if ((msb & 0xFFFFL) == (CUSTOM_IDENTIFIER & 0xFFFFL)) {
            return msb & 0xFFFFFFFFFFFF0000L;
        } else {
            throw  new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }


}
