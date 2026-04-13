package com.bumptech.glide.load;

import androidx.annotation.Nullable;
import java.io.IOException;

public final class HttpException extends IOException {
    public static final int UNKNOWN = -1;
    private static final long serialVersionUID = 1;
    private final int statusCode;

    public HttpException(int statusCode2) {
        this("Http request failed", statusCode2);
    }

    @Deprecated
    public HttpException(String message) {
        this(message, -1);
    }

    public HttpException(String message, int statusCode2) {
        this(message, statusCode2, (Throwable) null);
    }

    public HttpException(String message, int statusCode2, @Nullable Throwable cause) {
        super(message + ", status code: " + statusCode2, cause);
        this.statusCode = statusCode2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
