package com.yanzhenjie.andserver.error;

public class ServerInternalException extends HttpException {
    public ServerInternalException(String subMessage) {
        super(500, String.format("%s, %s.", new Object[]{"Server internal error", subMessage}));
    }

    public ServerInternalException(String subMessage, Throwable cause) {
        super(500, String.format("%s, %s.", new Object[]{"Server internal error", subMessage}), cause);
    }

    public ServerInternalException(Throwable cause) {
        super(500, String.format("%s.", new Object[]{"Server internal error"}), cause);
    }
}
