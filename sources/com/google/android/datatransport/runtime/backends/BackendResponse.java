package com.google.android.datatransport.runtime.backends;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BackendResponse {

    public enum Status {
        OK,
        TRANSIENT_ERROR,
        FATAL_ERROR,
        INVALID_PAYLOAD
    }

    public abstract long getNextRequestWaitMillis();

    public abstract Status getStatus();

    public static BackendResponse transientError() {
        return new AutoValue_BackendResponse(Status.TRANSIENT_ERROR, -1);
    }

    public static BackendResponse fatalError() {
        return new AutoValue_BackendResponse(Status.FATAL_ERROR, -1);
    }

    public static BackendResponse invalidPayload() {
        return new AutoValue_BackendResponse(Status.INVALID_PAYLOAD, -1);
    }

    public static BackendResponse ok(long nextRequestWaitMillis) {
        return new AutoValue_BackendResponse(Status.OK, nextRequestWaitMillis);
    }
}
