package org.webrtc;

import androidx.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class EncodedImage implements RefCounted {
    public final ByteBuffer buffer;
    public final long captureTimeMs;
    public final long captureTimeNs;
    public final int encodedHeight;
    public final int encodedWidth;
    public final FrameType frameType;
    @Nullable
    public final Integer qp;
    private final RefCountDelegate refCountDelegate;
    public final int rotation;
    public final String streamId;

    public enum FrameType {
        EmptyFrame(0),
        VideoFrameKey(3),
        VideoFrameDelta(4);
        
        private final int nativeIndex;

        private FrameType(int nativeIndex2) {
            this.nativeIndex = nativeIndex2;
        }

        public int getNative() {
            return this.nativeIndex;
        }

        @CalledByNative("FrameType")
        static FrameType fromNativeIndex(int nativeIndex2) {
            for (FrameType type : values()) {
                if (type.getNative() == nativeIndex2) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown native frame type: " + nativeIndex2);
        }
    }

    public void retain() {
        this.refCountDelegate.retain();
    }

    public void release() {
        this.refCountDelegate.release();
    }

    @CalledByNative
    private EncodedImage(ByteBuffer buffer2, @Nullable Runnable releaseCallback, int encodedWidth2, int encodedHeight2, long captureTimeNs2, FrameType frameType2, int rotation2, @Nullable Integer qp2, String streamId2) {
        this.buffer = buffer2;
        this.encodedWidth = encodedWidth2;
        this.encodedHeight = encodedHeight2;
        this.captureTimeMs = TimeUnit.NANOSECONDS.toMillis(captureTimeNs2);
        this.captureTimeNs = captureTimeNs2;
        this.frameType = frameType2;
        this.rotation = rotation2;
        this.qp = qp2;
        this.streamId = streamId2;
        this.refCountDelegate = new RefCountDelegate(releaseCallback);
    }

    @CalledByNative
    private ByteBuffer getBuffer() {
        return this.buffer;
    }

    @CalledByNative
    private int getEncodedWidth() {
        return this.encodedWidth;
    }

    @CalledByNative
    private int getEncodedHeight() {
        return this.encodedHeight;
    }

    @CalledByNative
    private long getCaptureTimeNs() {
        return this.captureTimeNs;
    }

    @CalledByNative
    private int getFrameType() {
        return this.frameType.getNative();
    }

    @CalledByNative
    private int getRotation() {
        return this.rotation;
    }

    @CalledByNative
    @Nullable
    private Integer getQp() {
        return this.qp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ByteBuffer buffer;
        private long captureTimeNs;
        private int encodedHeight;
        private int encodedWidth;
        private FrameType frameType;
        @Nullable
        private Integer qp;
        @Nullable
        private Runnable releaseCallback;
        private int rotation;
        private String streamId;

        private Builder() {
        }

        public Builder setBuffer(ByteBuffer buffer2, @Nullable Runnable releaseCallback2) {
            this.buffer = buffer2;
            this.releaseCallback = releaseCallback2;
            return this;
        }

        public Builder setEncodedWidth(int encodedWidth2) {
            this.encodedWidth = encodedWidth2;
            return this;
        }

        public Builder setEncodedHeight(int encodedHeight2) {
            this.encodedHeight = encodedHeight2;
            return this;
        }

        @Deprecated
        public Builder setCaptureTimeMs(long captureTimeMs) {
            this.captureTimeNs = TimeUnit.MILLISECONDS.toNanos(captureTimeMs);
            return this;
        }

        public Builder setCaptureTimeNs(long captureTimeNs2) {
            this.captureTimeNs = captureTimeNs2;
            return this;
        }

        public Builder setFrameType(FrameType frameType2) {
            this.frameType = frameType2;
            return this;
        }

        public Builder setRotation(int rotation2) {
            this.rotation = rotation2;
            return this;
        }

        public Builder setQp(@Nullable Integer qp2) {
            this.qp = qp2;
            return this;
        }

        public EncodedImage createEncodedImage() {
            return new EncodedImage(this.buffer, this.releaseCallback, this.encodedWidth, this.encodedHeight, this.captureTimeNs, this.frameType, this.rotation, this.qp, this.streamId);
        }
    }
}
