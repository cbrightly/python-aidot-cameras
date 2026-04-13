package com.google.android.datatransport.runtime.firebase.transport;

import com.google.firebase.encoders.annotations.Encodable;
import com.google.firebase.encoders.proto.Protobuf;

public final class GlobalMetrics {
    private static final GlobalMetrics DEFAULT_INSTANCE = new Builder().build();
    private final StorageMetrics storage_metrics_;

    GlobalMetrics(StorageMetrics storage_metrics_2) {
        this.storage_metrics_ = storage_metrics_2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Encodable.Ignore
    public StorageMetrics getStorageMetrics() {
        StorageMetrics storageMetrics = this.storage_metrics_;
        return storageMetrics == null ? StorageMetrics.getDefaultInstance() : storageMetrics;
    }

    @Protobuf(tag = 1)
    @Encodable.Field(name = "storageMetrics")
    public StorageMetrics getStorageMetricsInternal() {
        return this.storage_metrics_;
    }

    public static GlobalMetrics getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder {
        private StorageMetrics storage_metrics_ = null;

        Builder() {
        }

        public GlobalMetrics build() {
            return new GlobalMetrics(this.storage_metrics_);
        }

        public Builder setStorageMetrics(StorageMetrics storage_metrics_2) {
            this.storage_metrics_ = storage_metrics_2;
            return this;
        }
    }
}
