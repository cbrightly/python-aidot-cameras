package com.google.android.datatransport.runtime.firebase.transport;

import com.google.android.datatransport.runtime.ProtoEncoderDoNotUse;
import com.google.firebase.encoders.annotations.Encodable;
import com.google.firebase.encoders.proto.Protobuf;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ClientMetrics {
    private static final ClientMetrics DEFAULT_INSTANCE = new Builder().build();
    private final String app_namespace_;
    private final GlobalMetrics global_metrics_;
    private final List<LogSourceMetrics> log_source_metrics_;
    private final TimeWindow window_;

    ClientMetrics(TimeWindow window_2, List<LogSourceMetrics> log_source_metrics_2, GlobalMetrics global_metrics_2, String app_namespace_2) {
        this.window_ = window_2;
        this.log_source_metrics_ = log_source_metrics_2;
        this.global_metrics_ = global_metrics_2;
        this.app_namespace_ = app_namespace_2;
    }

    public byte[] toByteArray() {
        return ProtoEncoderDoNotUse.encode(this);
    }

    public void writeTo(OutputStream output) {
        ProtoEncoderDoNotUse.encode(this, output);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Encodable.Ignore
    public TimeWindow getWindow() {
        TimeWindow timeWindow = this.window_;
        return timeWindow == null ? TimeWindow.getDefaultInstance() : timeWindow;
    }

    @Protobuf(tag = 1)
    @Encodable.Field(name = "window")
    public TimeWindow getWindowInternal() {
        return this.window_;
    }

    @Protobuf(tag = 2)
    @Encodable.Field(name = "logSourceMetrics")
    public List<LogSourceMetrics> getLogSourceMetricsList() {
        return this.log_source_metrics_;
    }

    @Encodable.Ignore
    public GlobalMetrics getGlobalMetrics() {
        GlobalMetrics globalMetrics = this.global_metrics_;
        return globalMetrics == null ? GlobalMetrics.getDefaultInstance() : globalMetrics;
    }

    @Protobuf(tag = 3)
    @Encodable.Field(name = "globalMetrics")
    public GlobalMetrics getGlobalMetricsInternal() {
        return this.global_metrics_;
    }

    @Protobuf(tag = 4)
    public String getAppNamespace() {
        return this.app_namespace_;
    }

    public static ClientMetrics getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder {
        private String app_namespace_ = "";
        private GlobalMetrics global_metrics_ = null;
        private List<LogSourceMetrics> log_source_metrics_ = new ArrayList();
        private TimeWindow window_ = null;

        Builder() {
        }

        public ClientMetrics build() {
            return new ClientMetrics(this.window_, Collections.unmodifiableList(this.log_source_metrics_), this.global_metrics_, this.app_namespace_);
        }

        public Builder setWindow(TimeWindow window_2) {
            this.window_ = window_2;
            return this;
        }

        public Builder addLogSourceMetrics(LogSourceMetrics log_source_metrics_2) {
            this.log_source_metrics_.add(log_source_metrics_2);
            return this;
        }

        public Builder setLogSourceMetricsList(List<LogSourceMetrics> log_source_metrics_2) {
            this.log_source_metrics_ = log_source_metrics_2;
            return this;
        }

        public Builder setGlobalMetrics(GlobalMetrics global_metrics_2) {
            this.global_metrics_ = global_metrics_2;
            return this;
        }

        public Builder setAppNamespace(String app_namespace_2) {
            this.app_namespace_ = app_namespace_2;
            return this;
        }
    }
}
