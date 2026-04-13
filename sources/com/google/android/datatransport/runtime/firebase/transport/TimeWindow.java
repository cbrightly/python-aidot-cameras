package com.google.android.datatransport.runtime.firebase.transport;

import com.google.firebase.encoders.proto.Protobuf;

public final class TimeWindow {
    private static final TimeWindow DEFAULT_INSTANCE = new Builder().build();
    private final long end_ms_;
    private final long start_ms_;

    TimeWindow(long start_ms_2, long end_ms_2) {
        this.start_ms_ = start_ms_2;
        this.end_ms_ = end_ms_2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Protobuf(tag = 1)
    public long getStartMs() {
        return this.start_ms_;
    }

    @Protobuf(tag = 2)
    public long getEndMs() {
        return this.end_ms_;
    }

    public static TimeWindow getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder {
        private long end_ms_ = 0;
        private long start_ms_ = 0;

        Builder() {
        }

        public TimeWindow build() {
            return new TimeWindow(this.start_ms_, this.end_ms_);
        }

        public Builder setStartMs(long start_ms_2) {
            this.start_ms_ = start_ms_2;
            return this;
        }

        public Builder setEndMs(long end_ms_2) {
            this.end_ms_ = end_ms_2;
            return this;
        }
    }
}
