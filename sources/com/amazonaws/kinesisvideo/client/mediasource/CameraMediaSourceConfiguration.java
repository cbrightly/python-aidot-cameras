package com.amazonaws.kinesisvideo.client.mediasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;
import com.amazonaws.kinesisvideo.producer.StreamInfo;

public class CameraMediaSourceConfiguration implements MediaSourceConfiguration {
    private static final String MEDIA_SOURCE_DESCRIPTION = "Configuration for a camera media source";
    public static final String MEDIA_SOURCE_TYPE = "AbstractCameraMediaSource";
    private final Builder mBuilder;

    public static class Builder implements MediaSourceConfiguration.Builder<CameraMediaSourceConfiguration> {
        /* access modifiers changed from: private */
        public int mCameraFacing;
        /* access modifiers changed from: private */
        public String mCameraId;
        /* access modifiers changed from: private */
        public int mCameraOrientation;
        /* access modifiers changed from: private */
        public byte[] mCodecPrivateData;
        /* access modifiers changed from: private */
        public int mEncodingBitrate;
        /* access modifiers changed from: private */
        public int mFrameRate;
        /* access modifiers changed from: private */
        public long mFrameTimescale;
        /* access modifiers changed from: private */
        public int mGopDurationMillis;
        /* access modifiers changed from: private */
        public int mHorizontalResolution;
        /* access modifiers changed from: private */
        public boolean mIsAbsoluteTimecode;
        /* access modifiers changed from: private */
        public boolean mIsEncoderHardwareAccelerated;
        /* access modifiers changed from: private */
        public String mMimeType;
        /* access modifiers changed from: private */
        public StreamInfo.NalAdaptationFlags mNalAdaptationFlags;
        /* access modifiers changed from: private */
        public String mOutputFileName;
        /* access modifiers changed from: private */
        public int mRetentionPeriodInHours;
        /* access modifiers changed from: private */
        public int mVerticalResolution;

        public Builder withEncodingMimeType(String mimeType) {
            this.mMimeType = mimeType;
            return this;
        }

        public Builder withRetentionPeriodInHours(int retentionPeriodInHours) {
            this.mRetentionPeriodInHours = retentionPeriodInHours;
            return this;
        }

        public Builder withFrameRate(int frameRate) {
            this.mFrameRate = frameRate;
            return this;
        }

        public Builder withFileOutput(String outputFileName) {
            this.mOutputFileName = outputFileName;
            return this;
        }

        public Builder withCameraId(String cameraId) {
            this.mCameraId = cameraId;
            return this;
        }

        public Builder withHorizontalResolution(int horizontalResolution) {
            this.mHorizontalResolution = horizontalResolution;
            return this;
        }

        public Builder withVerticalResolution(int verticalResolution) {
            this.mVerticalResolution = verticalResolution;
            return this;
        }

        public Builder withCameraFacing(int facing) {
            this.mCameraFacing = facing;
            return this;
        }

        public Builder withCameraOrientation(int orientation) {
            this.mCameraOrientation = orientation;
            return this;
        }

        public Builder withEncodingBitRate(int bitrate) {
            this.mEncodingBitrate = bitrate;
            return this;
        }

        public Builder withIsEncoderHardwareAccelerated(boolean isAccelerated) {
            this.mIsEncoderHardwareAccelerated = isAccelerated;
            return this;
        }

        public Builder withCodecPrivateData(byte[] privateData) {
            this.mCodecPrivateData = privateData;
            return this;
        }

        public Builder withFrameTimeScale(long timescale) {
            this.mFrameTimescale = timescale;
            return this;
        }

        public Builder withGopDurationMillis(int gopDuration) {
            this.mGopDurationMillis = gopDuration;
            return this;
        }

        public Builder withNalAdaptationFlags(StreamInfo.NalAdaptationFlags nalAdaptationFlags) {
            this.mNalAdaptationFlags = nalAdaptationFlags;
            return this;
        }

        public Builder withIsAbsoluteTimecode(boolean isAbsoluteTimecode) {
            this.mIsAbsoluteTimecode = isAbsoluteTimecode;
            return this;
        }

        public String getMimeType() {
            return this.mMimeType;
        }

        public int getFrameRate() {
            return this.mFrameRate;
        }

        public int getmRetentionPeriodInHours() {
            return this.mRetentionPeriodInHours;
        }

        public int getHorizontalResolution() {
            return this.mHorizontalResolution;
        }

        public int getVerticalResolution() {
            return this.mVerticalResolution;
        }

        public String getOutputFileName() {
            return this.mOutputFileName;
        }

        public String getCameraId() {
            return this.mCameraId;
        }

        public int getCameraFacing() {
            return this.mCameraFacing;
        }

        public int getCameraOrientation() {
            return this.mCameraOrientation;
        }

        public int getEncodingBitrate() {
            return this.mEncodingBitrate;
        }

        public boolean isEncoderHardwareAccelerated() {
            return this.mIsEncoderHardwareAccelerated;
        }

        public int getGopDurationMillis() {
            return this.mGopDurationMillis;
        }

        public byte[] getCodecPrivateData() {
            return this.mCodecPrivateData;
        }

        public long getFrameTimescale() {
            return this.mFrameTimescale;
        }

        public StreamInfo.NalAdaptationFlags getNalAdaptationFlags() {
            return this.mNalAdaptationFlags;
        }

        public CameraMediaSourceConfiguration build() {
            return new CameraMediaSourceConfiguration(this);
        }
    }

    public CameraMediaSourceConfiguration(Builder builder) {
        this.mBuilder = builder;
    }

    public String getMediaSourceType() {
        return MEDIA_SOURCE_TYPE;
    }

    public String getMediaSourceDescription() {
        return MEDIA_SOURCE_DESCRIPTION;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCameraId() {
        return this.mBuilder.mCameraId;
    }

    public int getCameraFacing() {
        return this.mBuilder.mCameraFacing;
    }

    public int getCameraOrientation() {
        return this.mBuilder.mCameraOrientation;
    }

    public int getHorizontalResolution() {
        return this.mBuilder.mHorizontalResolution;
    }

    public int getVerticalResolution() {
        return this.mBuilder.mVerticalResolution;
    }

    public String getOutputFileName() {
        return this.mBuilder.mOutputFileName;
    }

    public int getFrameRate() {
        return this.mBuilder.mFrameRate;
    }

    public int getRetentionPeriodInHours() {
        return this.mBuilder.mRetentionPeriodInHours;
    }

    public int getBitRate() {
        return this.mBuilder.mEncodingBitrate;
    }

    @NonNull
    public String getEncoderMimeType() {
        return this.mBuilder.mMimeType;
    }

    public int getGopDurationMillis() {
        return this.mBuilder.mGopDurationMillis;
    }

    public boolean isEndcoderHardwareAccelerated() {
        return this.mBuilder.mIsEncoderHardwareAccelerated;
    }

    @Nullable
    public byte[] getCodecPrivateData() {
        return this.mBuilder.mCodecPrivateData;
    }

    public long getTimeScale() {
        return this.mBuilder.mFrameTimescale;
    }

    public StreamInfo.NalAdaptationFlags getNalAdaptationFlags() {
        return this.mBuilder.mNalAdaptationFlags;
    }

    public boolean getIsAbsoluteTimecode() {
        return this.mBuilder.mIsAbsoluteTimecode;
    }
}
