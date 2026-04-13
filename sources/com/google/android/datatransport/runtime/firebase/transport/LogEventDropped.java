package com.google.android.datatransport.runtime.firebase.transport;

import com.google.firebase.encoders.proto.ProtoEnum;
import com.google.firebase.encoders.proto.Protobuf;

public final class LogEventDropped {
    private static final LogEventDropped DEFAULT_INSTANCE = new Builder().build();
    private final long events_dropped_count_;
    private final Reason reason_;

    LogEventDropped(long events_dropped_count_2, Reason reason_2) {
        this.events_dropped_count_ = events_dropped_count_2;
        this.reason_ = reason_2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Protobuf(tag = 1)
    public long getEventsDroppedCount() {
        return this.events_dropped_count_;
    }

    @Protobuf(tag = 3)
    public Reason getReason() {
        return this.reason_;
    }

    public static LogEventDropped getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder {
        private long events_dropped_count_ = 0;
        private Reason reason_ = Reason.REASON_UNKNOWN;

        Builder() {
        }

        public LogEventDropped build() {
            return new LogEventDropped(this.events_dropped_count_, this.reason_);
        }

        public Builder setEventsDroppedCount(long events_dropped_count_2) {
            this.events_dropped_count_ = events_dropped_count_2;
            return this;
        }

        public Builder setReason(Reason reason_2) {
            this.reason_ = reason_2;
            return this;
        }
    }

    public enum Reason implements ProtoEnum {
        REASON_UNKNOWN(0),
        MESSAGE_TOO_OLD(1),
        CACHE_FULL(2),
        PAYLOAD_TOO_BIG(3),
        MAX_RETRIES_REACHED(4),
        INVALID_PAYLOD(5),
        SERVER_ERROR(6);
        
        private final int number_;

        private Reason(int number_2) {
            this.number_ = number_2;
        }

        public int getNumber() {
            return this.number_;
        }
    }
}
