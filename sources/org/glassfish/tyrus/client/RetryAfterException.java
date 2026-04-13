package org.glassfish.tyrus.client;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import org.glassfish.tyrus.core.HandshakeException;

public class RetryAfterException extends HandshakeException {
    private final Long delay;

    public RetryAfterException(String message, Long delay2) {
        super(TypedValues.PositionType.TYPE_PERCENT_WIDTH, message);
        this.delay = delay2;
    }

    public Long getDelay() {
        return this.delay;
    }
}
