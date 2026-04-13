package com.amazonaws.kinesisvideo.internal.mediasource.bytes;

import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;

public class BytesMediaSourceConfiguration implements MediaSourceConfiguration {
    private static final String MEDIA_SOURCE_DESCRIPTION = "Generates bytes in specific configuration. Useful for debugging";
    private static final String MEDIA_SOURCE_TYPE = "BytesMediaSource";
    private final Builder mBuilder;

    public static class Builder implements MediaSourceConfiguration.Builder<BytesMediaSourceConfiguration> {
        /* access modifiers changed from: private */
        public int fps;
        /* access modifiers changed from: private */
        public long retentionPeriodInHours;

        public Builder withFps(int fps2) {
            this.fps = fps2;
            return this;
        }

        public Builder withRetentionPeriodInHours(long retentionPeriodInHours2) {
            this.retentionPeriodInHours = retentionPeriodInHours2;
            return this;
        }

        public BytesMediaSourceConfiguration build() {
            return new BytesMediaSourceConfiguration(this);
        }
    }

    public BytesMediaSourceConfiguration(Builder builder) {
        this.mBuilder = builder;
    }

    public int getFps() {
        return this.mBuilder.fps;
    }

    public long getRetentionPeriodInHours() {
        return this.mBuilder.retentionPeriodInHours;
    }

    public String getMediaSourceType() {
        return MEDIA_SOURCE_TYPE;
    }

    public String getMediaSourceDescription() {
        return MEDIA_SOURCE_DESCRIPTION;
    }
}
