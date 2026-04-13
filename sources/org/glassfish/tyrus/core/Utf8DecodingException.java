package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class Utf8DecodingException extends WebSocketException {
    private static final CloseReason CLOSE_REASON = new CloseReason(CloseReason.a.NOT_CONSISTENT, LocalizationMessages.ILLEGAL_UTF_8_SEQUENCE());
    private static final long serialVersionUID = 7766051445796057L;

    public Utf8DecodingException() {
        super(CLOSE_REASON.b());
    }

    public CloseReason getCloseReason() {
        return CLOSE_REASON;
    }
}
