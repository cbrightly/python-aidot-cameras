package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;

public class ProducerException extends KinesisVideoException {
    public static final int STATUS_BASE = 0;
    public static final int STATUS_BUFFER_TOO_SMALL = 5;
    public static final int STATUS_FORMAT_ERROR = 7;
    public static final int STATUS_INTERNAL_ERROR = 12;
    public static final int STATUS_INVALID_ARG = 2;
    public static final int STATUS_INVALID_ARG_LEN = 3;
    public static final int STATUS_INVALID_HANDLE_ERROR = 8;
    public static final int STATUS_INVALID_OPERATION = 13;
    public static final int STATUS_NOT_IMPLEMENTED = 14;
    public static final int STATUS_NULL_ARG = 1;
    public static final int STATUS_OPEN_FILE_FAILED = 9;
    public static final int STATUS_OPERATION_TIMED_OUT = 15;
    public static final int STATUS_OUT_OF_MEMORY = 4;
    public static final int STATUS_READ_FILE_FAILED = 10;
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_UNEXPECTED_EOF = 6;
    public static final int STATUS_WRITE_TO_FILE_FAILED = 11;
    private final int mStatusCode;

    private static int statusCodeFromException(@NonNull Exception exception) {
        return 14;
    }

    public ProducerException(@NonNull Exception exception) {
        super((Throwable) exception);
        this.mStatusCode = statusCodeFromException(exception);
    }

    public ProducerException(@NonNull String message, int statusCode) {
        super(message + " StatusCode: 0x" + Integer.toHexString(statusCode));
        this.mStatusCode = statusCode;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }
}
