package com.amazonaws.kinesisvideo.client;

import com.amazonaws.kinesisvideo.auth.KinesisVideoCredentialsProvider;
import com.amazonaws.kinesisvideo.common.logging.OutputChannel;
import com.amazonaws.kinesisvideo.producer.StorageCallbacks;

public final class KinesisVideoClientConfiguration {
    private final KinesisVideoCredentialsProvider credentialsProvider;
    private final String endpoint;
    private final OutputChannel logChannel;
    private final String region;
    private final StorageCallbacks storageCallbacks;

    private KinesisVideoClientConfiguration(Builder builder) {
        this.region = builder.region;
        this.credentialsProvider = builder.credentialsProvider;
        this.storageCallbacks = builder.storageCallbacks;
        this.endpoint = builder.endpoint;
        this.logChannel = builder.logChannel;
    }

    public static Builder builder() {
        return new Builder();
    }

    /* access modifiers changed from: private */
    public static void sanitizeBuilder(Builder builder) {
        String region2 = builder.region;
        String endpoint2 = builder.endpoint;
        if (region2 == null && endpoint2 == null) {
            builder.withRegion("us-west-2");
            builder.withEndpoint(KinesisVideoClientConfigurationDefaults.getControlPlaneEndpoint(builder.region));
        }
        if (region2 == null) {
            builder.withRegion("us-west-2");
        }
        if (endpoint2 == null) {
            builder.withEndpoint(constructEndpoint(region2));
        }
    }

    private static String constructEndpoint(String region2) {
        return KinesisVideoClientConfigurationDefaults.getControlPlaneEndpoint(region2);
    }

    public String getServiceName() {
        return "kinesisvideo";
    }

    public String getRegion() {
        return this.region;
    }

    public KinesisVideoCredentialsProvider getCredentialsProvider() {
        return this.credentialsProvider;
    }

    public StorageCallbacks getStorageCallbacks() {
        return this.storageCallbacks;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public OutputChannel getLogChannel() {
        return this.logChannel;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public KinesisVideoCredentialsProvider credentialsProvider;
        /* access modifiers changed from: private */
        public String endpoint;
        /* access modifiers changed from: private */
        public OutputChannel logChannel;
        /* access modifiers changed from: private */
        public String region;
        /* access modifiers changed from: private */
        public StorageCallbacks storageCallbacks = KinesisVideoClientConfigurationDefaults.NO_OP_STORAGE_CALLBACKS;

        public Builder withRegion(String region2) {
            this.region = region2;
            return this;
        }

        public Builder withCredentialsProvider(KinesisVideoCredentialsProvider credentialsProvider2) {
            this.credentialsProvider = credentialsProvider2;
            return this;
        }

        public Builder withStorageCallbacks(StorageCallbacks storageCallbacks2) {
            this.storageCallbacks = storageCallbacks2;
            return this;
        }

        public Builder withEndpoint(String endpoint2) {
            this.endpoint = endpoint2;
            return this;
        }

        public Builder withLogChannel(OutputChannel logChannel2) {
            this.logChannel = logChannel2;
            return this;
        }

        public KinesisVideoClientConfiguration build() {
            KinesisVideoClientConfiguration.sanitizeBuilder(this);
            return new KinesisVideoClientConfiguration(this);
        }
    }
}
