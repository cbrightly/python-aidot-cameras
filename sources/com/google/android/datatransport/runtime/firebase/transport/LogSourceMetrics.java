package com.google.android.datatransport.runtime.firebase.transport;

import com.google.firebase.encoders.annotations.Encodable;
import com.google.firebase.encoders.proto.Protobuf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LogSourceMetrics {
    private static final LogSourceMetrics DEFAULT_INSTANCE = new Builder().build();
    private final List<LogEventDropped> log_event_dropped_;
    private final String log_source_;

    LogSourceMetrics(String log_source_2, List<LogEventDropped> log_event_dropped_2) {
        this.log_source_ = log_source_2;
        this.log_event_dropped_ = log_event_dropped_2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Protobuf(tag = 1)
    public String getLogSource() {
        return this.log_source_;
    }

    @Protobuf(tag = 2)
    @Encodable.Field(name = "logEventDropped")
    public List<LogEventDropped> getLogEventDroppedList() {
        return this.log_event_dropped_;
    }

    public static LogSourceMetrics getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder {
        private List<LogEventDropped> log_event_dropped_ = new ArrayList();
        private String log_source_ = "";

        Builder() {
        }

        public LogSourceMetrics build() {
            return new LogSourceMetrics(this.log_source_, Collections.unmodifiableList(this.log_event_dropped_));
        }

        public Builder setLogSource(String log_source_2) {
            this.log_source_ = log_source_2;
            return this;
        }

        public Builder addLogEventDropped(LogEventDropped log_event_dropped_2) {
            this.log_event_dropped_.add(log_event_dropped_2);
            return this;
        }

        public Builder setLogEventDroppedList(List<LogEventDropped> log_event_dropped_2) {
            this.log_event_dropped_ = log_event_dropped_2;
            return this;
        }
    }
}
