package org.webrtc;

import android.graphics.Matrix;
import androidx.annotation.Nullable;
import java.nio.ByteBuffer;

public class VideoFrame implements RefCounted {
    private final Buffer buffer;
    private String channelId;
    private String frameTimestamp;
    private final int isKeyFrame;
    private String liveType;
    private final int rotation;
    private final String streamId;
    private final long timestampNs;

    public interface Buffer extends RefCounted {
        @CalledByNative("Buffer")
        Buffer cropAndScale(int i, int i2, int i3, int i4, int i5, int i6);

        @CalledByNative("Buffer")
        int getBufferType();

        @CalledByNative("Buffer")
        int getHeight();

        @CalledByNative("Buffer")
        int getWidth();

        @CalledByNative("Buffer")
        void release();

        @CalledByNative("Buffer")
        void retain();

        @CalledByNative("Buffer")
        @Nullable
        I420Buffer toI420();
    }

    public interface I420Buffer extends Buffer {
        int getBufferType();

        @CalledByNative("I420Buffer")
        ByteBuffer getDataU();

        @CalledByNative("I420Buffer")
        ByteBuffer getDataV();

        @CalledByNative("I420Buffer")
        ByteBuffer getDataY();

        @CalledByNative("I420Buffer")
        int getStrideU();

        @CalledByNative("I420Buffer")
        int getStrideV();

        @CalledByNative("I420Buffer")
        int getStrideY();
    }

    public static class VideoFrameLiveType {
        public String live = "0";
        public String sd = "1";
        public String unknown = "-1";

        VideoFrameLiveType() {
        }
    }

    public interface TextureBuffer extends Buffer {
        int getTextureId();

        Matrix getTransformMatrix();

        Type getType();

        public enum Type {
            OES(36197),
            RGB(3553);
            
            private final int glTarget;

            private Type(int glTarget2) {
                this.glTarget = glTarget2;
            }

            public int getGlTarget() {
                return this.glTarget;
            }
        }
    }

    @CalledByNative
    public VideoFrame(Buffer buffer2, int rotation2, long timestampNs2, String streamId2, int isKeyFrame2) {
        if (buffer2 == null) {
            throw new IllegalArgumentException("buffer not allowed to be null");
        } else if (rotation2 % 90 == 0) {
            this.buffer = buffer2;
            this.rotation = rotation2;
            this.timestampNs = timestampNs2;
            this.streamId = streamId2;
            this.isKeyFrame = isKeyFrame2;
            if (streamId2 != null || streamId2.length() > 0) {
                String[] array = streamId2.split("-");
                if (array.length >= 3) {
                    this.liveType = array[0];
                    this.channelId = array[1];
                    this.frameTimestamp = array[2];
                }
            }
        } else {
            throw new IllegalArgumentException("rotation must be a multiple of 90");
        }
    }

    @CalledByNative
    public Buffer getBuffer() {
        return this.buffer;
    }

    @CalledByNative
    public int getRotation() {
        return this.rotation;
    }

    @CalledByNative
    public long getTimestampNs() {
        return this.timestampNs;
    }

    public String getLiveType() {
        return this.liveType;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getFrameTimestamp() {
        return this.frameTimestamp;
    }

    @CalledByNative
    public String getStreamId() {
        return this.streamId;
    }

    @CalledByNative
    public int getIsKeyFrame() {
        return this.isKeyFrame;
    }

    public int getRotatedWidth() {
        if (this.rotation % 180 == 0) {
            return this.buffer.getWidth();
        }
        return this.buffer.getHeight();
    }

    public int getRotatedHeight() {
        if (this.rotation % 180 == 0) {
            return this.buffer.getHeight();
        }
        return this.buffer.getWidth();
    }

    public void retain() {
        this.buffer.retain();
    }

    @CalledByNative
    public void release() {
        this.buffer.release();
    }
}
